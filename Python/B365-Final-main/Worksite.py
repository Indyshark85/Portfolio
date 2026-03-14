import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier, plot_tree
from sklearn.preprocessing import LabelEncoder
from sklearn.metrics import classification_report, accuracy_score, ConfusionMatrixDisplay
import matplotlib.pyplot as plt

# Load the dataset
df = pd.read_csv("income.csv")

# Drop rows with missing values (represented by '?')
df_clean = df.replace('?', pd.NA).dropna()

# Most missing values in the set are in these columns so this will clean missing data
df_clean.drop(['occupation', 'relationship', 'capital.gain', 'capital.loss'], axis=1, inplace=True)
df_clean['workclass'] = df_clean['workclass'].replace({
    'Self-emp-not-inc': 'Self-employed',
    'Self-emp-inc': 'Self-employed',
    'Local-gov': 'Government',
    'State-gov': 'Government',
    'Federal-gov': 'Government',
    '?': pd.NA
})
df_clean = df_clean[df_clean['native.country'] == 'United-States']
df_clean.drop(columns='native.country', inplace=True)
df_clean['education'] = df_clean['education'].replace({
    'Assoc-acdm': 'College',
    'Assoc-voc': 'College',
    'Some-college': 'College',
    'HS-grad': 'No College',
    '11th': 'No College', '10th': 'No College', '7th-8th': 'No College',
    'Preschool': 'No College', '1st-4th': 'No College', '5th-6th': 'No College',
    '9th': 'No College', '12th': 'No College'
})
df_clean['marital.status'] = df_clean['marital.status'].replace({
    'Separated': 'Single',
    'Divorced': 'Single',
    'Never-married': 'Single',
    'Married-spouse-absent': 'Single',
    'Married-civ-spouse': 'Married',
    'Married-AF-spouse': 'Married'
})

weights = df_clean['fnlwgt']
df_clean.drop(columns='fnlwgt', inplace=True)

# Group all government workclasses together into one and self employed into one. Remove '?'
# Drop everything except United-States for native.country (cleans rest of missing data)
# Split on age, education, education years over/under expected, marital status, race, sex, hrs/week
# Income is the last thing
# Education only hs grad-college grad, prune anything else (some college as well). Potentially group all non-hs stuff together
# All Assoc types into one group (2 yr)
# Ignore weight for decision tree calculation. Maybe factor it in at the end for some frequency map or something
# Split age/hrs per week by median number
# Combine Separated, Divorced, Married-spouse-absent, Never-married into one category. Grouping together based on income type (single household/married household)


# Encode categorical features
df_encoded = df_clean.copy()
label_encoders = {}

for column in df_encoded.select_dtypes(include='object').columns:
    le = LabelEncoder()
    df_encoded[column] = le.fit_transform(df_encoded[column])
    label_encoders[column] = le

# Split features and target
X = df_encoded.drop(columns='income')
y = df_encoded['income']

# Split into training and test sets
X_train, X_test, y_train, y_test, weights_train, weights_test = train_test_split(
    X, y, weights, test_size=0.2, random_state=42
)

# ---- APPLY COST COMPLEXITY PRUNING TO REMOVE UNNECESSARY BRANCHES ----
# Compute pruning path to determine optimal alpha
path = DecisionTreeClassifier(random_state=42).cost_complexity_pruning_path(X_train, y_train, sample_weight=weights_train)
ccp_alphas = path.ccp_alphas[:-1]  # Exclude the maximum alpha

# Train multiple pruned trees and choose the best pruning level
models = [DecisionTreeClassifier(random_state=42, max_depth=4, min_samples_leaf=10, ccp_alpha=alpha).fit(X_train, y_train, sample_weight=weights_train) for alpha in ccp_alphas]

# Find the best pruning level based on accuracy
accuracies = [accuracy_score(y_test, model.predict(X_test)) for model in models]
best_alpha = ccp_alphas[accuracies.index(max(accuracies))]

# Train final pruned model with best alpha value
model = DecisionTreeClassifier(random_state=42, max_depth=4, min_samples_leaf=10, ccp_alpha=best_alpha)
model.fit(X_train, y_train, sample_weight=weights_train)

# Make predictions
y_pred = model.predict(X_test)

# Evaluate the model
accuracy = accuracy_score(y_test, y_pred)
report = classification_report(y_test, y_pred)

print(f"Accuracy: {accuracy:.4f}")
print("Classification Report:")
print(report)

def get_split_conditions(decision_tree, feature_names):
    tree_ = decision_tree.tree_
    feature = tree_.feature
    threshold = tree_.threshold
    node_labels = []

    for i in range(tree_.node_count):
        if feature[i] != -2:
            name = feature_names[feature[i]]
            thresh = threshold[i]
            label = f"{name} <= {thresh:.2f}"
        else:
            class_name = decision_tree.classes_[tree_.value[i].argmax()]
            label = ">50k" if class_name == 0 else "<=50k"
        node_labels.append(label)

    return node_labels

# Generate custom labels
custom_labels = get_split_conditions(model, X.columns.tolist())

# Plot the tree with custom labels
plt.figure(figsize=(20, 12))
plot_tree(
    model,
    feature_names=X.columns.tolist(),
    class_names=label_encoders['income'].classes_,
    filled=True,
    impurity=False,
    rounded=True,
    label='none'  # Prevent auto-labeling
)

# Overwrite default labels with custom ones
ax = plt.gca()
node_index = 0  # Start from the root node
for i, text in enumerate(ax.texts):
    if "True" not in text.get_text() and "False" not in text.get_text():  # Skip directional labels
        if node_index < len(custom_labels):
            text.set_text(custom_labels[node_index])
            text.set_fontsize(18)  # ---- INCREASED FONT SIZE FOR SPLIT CONDITIONS ----
            node_index += 1

plt.title("Optimized Decision Tree with Custom Labels", fontsize=24)  # ---- INCREASED TITLE FONT SIZE ----
plt.savefig("decision_tree_highres_custom.pdf", format='pdf', dpi=300, bbox_inches='tight')
plt.close()



"""
# Make predictions
y_pred = model.predict(X_test)


accuracies = [accuracy_score(y_test, model.predict(X_test)) for model in models]
best_alpha = ccp_alphas[accuracies.index(max(accuracies))]

# Evaluate the model
accuracy = accuracy_score(y_test, y_pred)
report = classification_report(y_test, y_pred)

print(f"Accuracy: {accuracy:.4f}")
print("Classification Report:")
print(report)


# Create custom labels for each node
def get_split_conditions(decision_tree, feature_names):
    tree_ = decision_tree.tree_
    feature = tree_.feature
    threshold = tree_.threshold
    node_labels = []

    for i in range(tree_.node_count):
        if feature[i] != -2:
            name = feature_names[feature[i]]
            thresh = threshold[i]
            label = f"{name} <= {thresh:.2f}"
        else:
            class_name = decision_tree.classes_[tree_.value[i].argmax()]
            label = ">50k" if class_name == 0 else "<=50k"
        node_labels.append(label)

    return node_labels


# Generate custom labels
custom_labels = get_split_conditions(model, X.columns.tolist())

# Plot the tree with custom labels
plt.figure(figsize=(60, 40))
plot_tree(
    model,
    feature_names=X.columns.tolist(),
    class_names=label_encoders['income'].classes_,
    filled=True,
    impurity=False,
    proportion=False,
    rounded=True,
    fontsize=14,
    precision=2,
    label='none'  # Prevent auto-labeling
)

# Overwrite the default labels with custom ones
ax = plt.gca()
node_index = 0  # Start from the root node
for i, text in enumerate(ax.texts):
    # Skip the labels on the arrow edges (true/false directions)
    if "True" not in text.get_text() and "False" not in text.get_text():
        if node_index < len(custom_labels):
            text.set_text(custom_labels[node_index])
            node_index += 1

plt.title("High-Resolution Decision Tree", fontsize=24)

# Save as high-resolution PDF
plt.savefig("decision_tree_highres.pdf", format='pdf', dpi=300, bbox_inches='tight')
plt.close()
"""
# Plot feature importances
importances = pd.Series(model.feature_importances_, index=X.columns)
importances.sort_values().plot(kind='barh', figsize=(10, 6), title="Feature Importances")
plt.xlabel("Importance")
plt.show()

# Plot confusion matrix
ConfusionMatrixDisplay.from_estimator(
    model, 
    X_test, 
    y_test,
    cmap='Reds'
    )
plt.title("Decision Tree Confusion Matrix")
plt.show()
