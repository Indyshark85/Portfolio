package avl;

import trees.EmptyTreeExc;
import trees.TreePrinter;
import org.jetbrains.annotations.NotNull;

public record AVLNode<E extends Comparable<E>>
        (@NotNull E value,
         @NotNull AVLTree<E> left,
         @NotNull AVLTree<E> right,
         int height)
        implements AVLTree<E> {

    // Constructors -------------------------------------------------------------------------

    public AVLNode(@NotNull E value) {
        this(value, new EmptyAVL<>(), new EmptyAVL<>());
    }

    public AVLNode(@NotNull E value, @NotNull AVLTree<E> left, @NotNull AVLTree<E> right) {
        this(value, left, right, 1 + Math.max(left.height(), right.height()));
    }

    // Simple one-liner methods ------------------------------------------------------------

    public boolean isEmpty() { return false; }
    public int height() {
        return height;
    }
    public int balanceFactor() {
        return left.height() - right.height();
    }
    public boolean isWellFormed() {
        return left.isWellFormed() && right.isWellFormed() && Math.abs(balanceFactor()) <= 1;
    }

    // Search methods -----------------------------------------------------------------------

    public boolean contains(@NotNull E searchValue) {
        try{
            int cmp = searchValue.compareTo(value);
            if(cmp ==0) return true;
            else if (cmp < 0) return left.contains(searchValue);
            else return right.contains(searchValue);
        }catch (Exception e) {
            throw new RuntimeException("Comparison failed");
        }
        // in case comparison of searchValue to value fails, throw new RuntimeException("Comparison failed");
    }

    public @NotNull E findMin() throws EmptyTreeExc {
        if (left.isEmpty()) return value;
        return left.findMin();
    }

    // Insertion and deletion methods ------------------------------------------------------

    public @NotNull AVLTree<E> insert(@NotNull E newValue) {
        try{
            int cmp = newValue.compareTo(value);

            if(cmp<0)
                return mkBalanced(value,left.insert(newValue),right);
            else if(cmp>0)
                return mkBalanced(value,left,right.insert(newValue));
            else
                return this;

        }catch (Exception e){
            throw new RuntimeException("Comparison failed");
        }
        // in case comparison of newValue to value fails, throw new RuntimeException("Comparison failed");
    }

    public @NotNull AVLTree<E> remove(@NotNull E removeValue) throws EmptyTreeExc {
        int cmp;
        try {
            cmp = removeValue.compareTo(value);
        } catch (Exception e) {
            throw new RuntimeException("Comparison failed");
        }

        if (cmp < 0) {
            return mkBalanced(value, left.remove(removeValue), right);
        } else if (cmp > 0) {
            return mkBalanced(value, left, right.remove(removeValue));
        } else {
            // this node should be deleted
            if (left.isEmpty()) return right;
            if (right.isEmpty()) return left;
            return mergeSubtrees(left, right);
        }
        // in case comparison of removeValue to value fails, throw new RuntimeException("Comparison failed");
    }

    public static <E extends Comparable<E>> @NotNull AVLTree<E> mergeSubtrees(
            @NotNull AVLTree<E> left,
            @NotNull AVLTree<E> right) {

        try { return mkBalanced(right.findMin(), left, right.removeMin()); }
        catch (EmptyTreeExc e) { return left; }
    }

    public @NotNull AVLTree<E> removeMin() {
        try { return mkBalanced(value, left.removeMin(), right); }
        catch (EmptyTreeExc e) { return right; }
    }

    // Rotations ----------------------------------------------------------------------------

    public static <E extends Comparable<E>> AVLTree<E> mkBalanced
            (@NotNull E value,
             @NotNull AVLTree<E> left,
             @NotNull AVLTree<E> right) {
        return new AVLNode<>(value, left, right).rotate();
    }

    public @NotNull AVLTree<E> rotate() {
        int bf = balanceFactor();
        if (bf>1){ //left heavy
            if(left.balanceFactor() >= 0)
                return rotateRight();
            else
                return rotateLeftRight();
        } else if (bf <-1) { //right heavy
            if(right.balanceFactor()<=0)
                return rotateLeft();
            else
                return rotateRightLeft();
        }
        return this;
    }

    public @NotNull AVLTree<E> rotateRight() {
        if (!(left instanceof AVLNode<E> leftNode))
            throw new RuntimeException("Right rotation failed");

        // new root becomes leftNode.value
        AVLTree<E> newLeft = leftNode.left();
        AVLTree<E> middle = leftNode.right();

        return new AVLNode<>(
                leftNode.value,
                newLeft,
                new AVLNode<>(value, middle, right)
        );
    }

    public @NotNull AVLTree<E> rotateLeft() {
        if (!(right instanceof AVLNode<E> rightNode))
            throw new RuntimeException("Left rotation failed");

        // new root becomes rightNode.value
        AVLTree<E> newRight = rightNode.right();
        AVLTree<E> middle = rightNode.left();

        return new AVLNode<>(
                rightNode.value,
                new AVLNode<>(value, left, middle),
                newRight
        );
    }

    public AVLTree<E> rotateLeftRight() {
        AVLNode<E> leftNode = (AVLNode<E>) left;
        AVLTree<E> newLeft = ((AVLNode<E>) left).rotateLeft();
        return new AVLNode<>(value, newLeft, right).rotateRight();
    }

    public AVLTree<E> rotateRightLeft() {
        AVLNode<E> rightNode = (AVLNode<E>) right;
        AVLTree<E> newRight = ((AVLNode<E>) right).rotateRight();
        return new AVLNode<>(value, left, newRight).rotateLeft();
    }


    // TreePrinter.PrintableNode interface methods ------------------------------------------

    public TreePrinter.PrintableNode getLeft() {
        return left.isEmpty() ? null : left;
    }
    public TreePrinter.PrintableNode getRight() {
        return right.isEmpty() ? null : right;
    }
    public String getText() {
        return value.toString();
    }
}
