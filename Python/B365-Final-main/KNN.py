import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder, StandardScaler
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import classification_report, accuracy_score, ConfusionMatrixDisplay
from sklearn.decomposition import PCA
import matplotlib.pyplot as plt

# Load the dataset
df = pd.read_csv("income.csv")

# Drop rows with missing values (represented by '?')
df_clean = df.replace('?', pd.NA).dropna()

# Most missing values in the set are in these columns so this will clean missing data
df_clean.drop(['occupation', 'relationship', 'capital.gain', 'capital.loss'], axis=1, inplace=True)


# Encode categorical columns
categorical_cols = df.select_dtypes(include='object').columns.drop('income')
encoders = {col: LabelEncoder().fit(df[col]) for col in categorical_cols}
for col, encoder in encoders.items():
    df[col] = encoder.transform(df[col])

# Encode the target
label_encoder = LabelEncoder()
df['income'] = label_encoder.fit_transform(df['income'])  # >50K → 1, <=50K → 0

# Use fnlwgt as weight
sample_weights = df['fnlwgt']

# Prepare features and target
X = df.drop(columns=['income', 'fnlwgt'])
y = df['income']

# Train/test split
X_train, X_test, y_train, y_test, w_train, w_test = train_test_split(
    X, y, sample_weights, test_size=0.2, random_state=42
)

# Scale features
scaler = StandardScaler()
X_train = scaler.fit_transform(X_train)
X_test = scaler.transform(X_test)

# Create and fit k-NN model
knn = KNeighborsClassifier(n_neighbors=5, weights='distance')  # standard distance-weighted kNN
knn.fit(X_train, y_train)

# Predict
y_pred = knn.predict(X_test)

# Evaluate the model
accuracy = accuracy_score(y_test, y_pred, sample_weight=w_test)
report = classification_report(y_test, y_pred)

print(f"Accuracy: {accuracy:.4f}")
print("Classification Report:")
print(report)

# Plot confusion matrix
ConfusionMatrixDisplay.from_estimator(
    knn, 
    X_test, 
    y_test,
    cmap='Blues'
    )
plt.title("KNN Confusion Matrix")
plt.show()



# The following is to make a visual representation of the classification


# Visualize decision boundaries in 2D using PCA
pca = PCA(n_components=2)
X_train_2D = pca.fit_transform(X_train)
X_test_2D = pca.transform(X_test)

knn_2D = KNeighborsClassifier(n_neighbors=5, weights='distance')
knn_2D.fit(X_train_2D, y_train)

# Create a mesh grid for plotting decision boundaries
x_min, x_max = X_train_2D[:, 0].min() - 1, X_train_2D[:, 0].max() + 1
y_min, y_max = X_train_2D[:, 1].min() - 1, X_train_2D[:, 1].max() + 1
xx, yy = np.meshgrid(np.linspace(x_min, x_max, 300),
                     np.linspace(y_min, y_max, 300))

Z = knn_2D.predict(np.c_[xx.ravel(), yy.ravel()])
Z = Z.reshape(xx.shape)

plt.figure(figsize=(10, 6))
plt.contourf(xx, yy, Z, cmap=plt.cm.coolwarm, alpha=0.3)
scatter = plt.scatter(X_test_2D[:, 0], X_test_2D[:, 1], c=y_pred, cmap=plt.cm.coolwarm, edgecolor='k', s=40)
plt.xlabel("PCA Component 1")
plt.ylabel("PCA Component 2")
plt.title("k-NN Classification Visualization (2D PCA)")
plt.legend(*scatter.legend_elements(), title="Predicted Class")
plt.grid(True)
plt.show()



# Identify misclassified points
misclassified = y_pred != y_test
correct = ~misclassified

# Create figure
plt.figure(figsize=(10, 6))

# Plot correctly classified points
plt.scatter(
    X_test_2D[correct, 0], X_test_2D[correct, 1],
    c='green', marker='o', label='Correct', alpha=0.6, edgecolor='k'
)

# Plot misclassified points
plt.scatter(
    X_test_2D[misclassified, 0], X_test_2D[misclassified, 1],
    c='red', marker='x', label='Misclassified', alpha=0.8
)

plt.xlabel("PCA Component 1")
plt.ylabel("PCA Component 2")
plt.title("PCA 2D - Correct vs Misclassified Points")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.show()




# Side-by-side comparison: True vs Predicted labels
fig, axes = plt.subplots(1, 2, figsize=(14, 6))

# True labels
axes[0].scatter(X_test_2D[:, 0], X_test_2D[:, 1], c=y_test, cmap=plt.cm.coolwarm, edgecolor='k', s=40)
axes[0].set_title("True Labels (PCA 2D)")
axes[0].set_xlabel("PCA Component 1")
axes[0].set_ylabel("PCA Component 2")
axes[0].grid(True)

# Predicted labels
axes[1].scatter(X_test_2D[:, 0], X_test_2D[:, 1], c=y_pred, cmap=plt.cm.coolwarm, edgecolor='k', s=40)
axes[1].set_title("Predicted Labels (PCA 2D)")
axes[1].set_xlabel("PCA Component 1")
axes[1].set_ylabel("PCA Component 2")
axes[1].grid(True)

plt.tight_layout()
plt.show()
