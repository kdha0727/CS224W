public interface IGraph {
    void insertVertex();

    void deleteVertex(int n);

    void insertEdge(int u, int v, int w);

    void deleteEdge(int u, int v);

    int[][] matrix();

    int size();
}
