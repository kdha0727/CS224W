import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class Graph implements IGraph {

    private int size;
    private int[][] matrix;

    public Graph(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
       if (line == null) return;
        String[] words = line.split(" ");
        size = Integer.parseInt(words[0]);
        matrix = new int[size][size];
        for (int i = 0; i < size; i++) for (int j = 0; j < size; j++) matrix[i][j] = 0;
        int iteration = Integer.parseInt(words[1]);
        for (int i = 0; i < iteration; i++) {
            line = reader.readLine();
       if (line == null) return;
            words = line.split(" ");
            matrix[Integer.parseInt(words[0])][Integer.parseInt(words[1])] = Integer.parseInt(words[2]);
        }
    }

    /**
     * Input: None
     *
     *  Extend the number of verticies and label it as the last number.
     */
    @Override
    public void insertVertex() {
        int[][] new_matrix = new int[size + 1][size + 1];
        for (int i = 0; i < size; i++) {
            System.arraycopy(matrix[i], 0, new_matrix[i], 0, size);
            new_matrix[i][size] = 0;
        }
        for (int i = 0; i < size; i++) new_matrix[size][i] = 0;
        matrix = new_matrix;
        size++;
    }

    /**
     * Input:
     *  + n: the node to delete
     *
     *  Delete the node n and its connections to other nodes.
     */
    @Override
    public void deleteVertex(int n) {
        if (n < 0 || n >= size) return;
        int[][] new_matrix = new int[size - 1][size - 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, new_matrix[i], 0, n);
            System.arraycopy(matrix[i], n + 1, new_matrix[i], n, size - n - 1);
        }
        for (int i = n; i < size - 1; i++) {
            System.arraycopy(matrix[i + 1], 0, new_matrix[i], 0, n);
            System.arraycopy(matrix[i + 1], n + 1, new_matrix[i], n, size - n - 1);
        }
        matrix = new_matrix;
        size--;
    }

    /**
     * Input:
     *  + u: start vertex
     *  + v: end vertex
     *  + w: weight of the edge
     *
     *  Insert an edge from u to v with weight w.
     */
    @Override
    public void insertEdge(int u, int v, int w) {
        matrix[u][v] = w;
    }

    /**
     * Input:
     *  + u: start vertex
     *  + v: end vertex
     *
     *  Delete an edge from u to v.
     */
    @Override
    public void deleteEdge(int u, int v) {
        matrix[u][v] = 0;
    }

    /**
     * Input: None
     *
     *  Return the adjacency matrix.
     */
    @Override
    public int[][] matrix() {
        return matrix;
    }

    /**
     * Input: None
     *
     *  Return the size.
     */
    @Override
    public int size() {
        return size;
    }

}
