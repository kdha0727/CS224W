import java.util.List;
import java.util.ArrayList;

public final class SCC {

    private SCC() {}

    /**
     * Input:
     *  + G: directed graph
     *  + u: start vertex
     *  + v: end vertex
     *
     *  Return whether there is a path from u to v or not.
     */
    public static boolean path(IGraph G, int u, int v) {
        return performDFS(G.matrix(), u)[v];
    }

    /**
     * Input:
     *  + G: directed graph
     *
     *  Find the number of strongly connected components in G.
     */
    public static int connectivity(IGraph G) {
        int[][] originalMatrix = G.matrix(), reversedMatrix = reverse(originalMatrix);
        boolean[] visited = new boolean[originalMatrix.length];
        for (int i = 0; i < originalMatrix.length; i++)
            visited[i] = false;
        int result = 0;
        for (int i = 0; i < originalMatrix.length; i++)
            if (!visited[i]) {
                boolean[] original = performDFS(originalMatrix, i);
                boolean[] reversed = performDFS(reversedMatrix, i);
                for (int j = 0; j < originalMatrix.length; j++)
                    if (original[j] && reversed[j])
                        visited[j] = true;
                result++;
            }
        return result;
    }

    private static int[][] reverse(int[][] matrix) {
        int[][] new_matrix = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++)
                new_matrix[j][i] = matrix[i][j];
        return new_matrix;
    }

    private static boolean[] performDFS(int[][] matrix, int start) {

        class DFS {

            private int[][] matrix;
            private boolean[] visited;

            public DFS(int[][] matrix, int start) {
                DFS.this.matrix = matrix;
                DFS.this.visited = new boolean[matrix.length];
                for (int i = 0; i < matrix.length; i++) {
                    DFS.this.visited[i] = false;
                }
                DFS.this.search(start);
            }

            private void search(int idx) {
                DFS.this.visited[idx] = true;
                for (int i = 0; i < DFS.this.matrix.length; i++)
                    if (DFS.this.matrix[idx][i] == 1 && !DFS.this.visited[i])
                        search(i);
            }

            public boolean[] getResult() { return DFS.this.visited; }

        }

        return new DFS(matrix, start).getResult();

    }

}
