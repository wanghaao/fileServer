package fileserver.cache;

/**
 * @description: hashArray 的节点
 * @author: wang hao
 */
class FileListNode {
    String filePath;
    int counter;
    String other;// 预留的

    public FileListNode(String filePath, int counter, String other) {
        this.filePath = filePath;
        this.counter = counter;
        this.other = other;
    }

    public FileListNode(String filePath, int counter) {
        this.filePath = filePath;
        this.counter = counter;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
