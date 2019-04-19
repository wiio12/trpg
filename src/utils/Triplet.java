package utils;

public class Triplet<L, M, R> {

    private L left;
    private M middle;
    private R right;
    private String name;

    public Triplet(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.name = null;
    }

    public Triplet(L left, M middle, R right, String name){
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.name = name;
    }

    public L getLeft() { return left; }
    public M getMiddle() { return middle; }
    public R getRight() { return right; }

    public void setLeft(L left) {
        this.left = left;
    }

    public void setMiddle(M middle) {
        this.middle = middle;
    }

    public void setRight(R right) {
        this.right = right;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}