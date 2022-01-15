import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

public final class MST {

    private MST() {}

    /**
     * Input:
     *  + G: directed graph
     *  + u: start vertex
     *  + v: end vertex
     *
     *  Return the weight of the path from u to v.
     *  If such path doesn't exist, return -1.
     */
    public static int shortestPath(IGraph G, int u, int v) {

        // Dijkstra`s Algorithm
        int[][] matrix = G.matrix();

        // Initialize Distance Array
        int[] dist = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++)
            dist[i] = Integer.MAX_VALUE;

        // Distance to the source is 0
        dist[u] = 0;

        // Initialize `settled` list
        List<Integer> settled = new ArrayList<>();

        // Initialize priority queue
        PriorityQueue<Node> pq = new PriorityQueue<>(matrix.length);
        pq.add(new Node(u, 0));

        while (settled.size() != matrix.length) {

            // Terminating condition check when
            // the priority queue is empty, return
            if (pq.isEmpty())
                break;

            // Removing the minimum distance node
            // from the priority queue
            int to = pq.remove().to;

            // Adding the node whose distance is
            // finalized
            if (settled.contains(to))

                // Continue keyword skips exwcution for
                // following check
                continue;

            // We don't have to call e_Neighbors(u)
            // if u is already present in the settled set.
            settled.add(to);

            // All the neighbors of v
            for (int i = 0; i < matrix.length; i++) {

                if (matrix[to][i] == 0) continue;

                // If current node hasn't already been processed
                if (!settled.contains(i)) {
                    int newDistance = dist[to] + matrix[to][i];

                    // If new distance is cheaper in cost
                    if (newDistance < dist[i])
                        dist[i] = newDistance;

                    // Add the current node to the queue
                    pq.add(new Node(i, dist[i]));
                }

            }

        }

        return dist[v] != Integer.MAX_VALUE? dist[v]: -1;

    }

    /**
     * Input:
     *  + G: directed graph
     *
     *  Return the total weight of the MST in G'.
     *  If MST doesn't exist, return -1.
     */
    public static int findMST(IGraph G) {

        int[][] matrix = G.matrix(), new_matrix = matrix.clone();
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++) {
                new_matrix[i][j] += new_matrix[j][i];
            }

        boolean[] visited = new boolean[matrix.length];
        PriorityQueue<Node> pq = new PriorityQueue<>();

        // 1번 노드부터 출발
        pq.add(new Node(0, 0));

        int res = 0;
        int cnt = 0;

        while(!pq.isEmpty()) {

            Node edge = pq.poll();

            // 이미 확인한 정점이면 pass
            if(visited[edge.to]) continue;

            res += edge.value;
            visited[edge.to] = true;

            // 모든 노드를 방문했다면 return
            if(++cnt == matrix.length) return res;

            for (int i = 0; i < matrix.length; i++) {
                // 연결된 노드들 중 방문하지 않은 노드 찾기
                int value = matrix[edge.to][i];
                if (value == 0 || visited[i]) continue;
                Node next = new Node(i ,value);
                pq.add(next);
            }
        }

        if (cnt != matrix.length) return -1;
        return res;

    }

    private static class Node implements Comparable<Node> {

        public int to, value = Integer.MAX_VALUE;

        @Override
        public int compareTo(Node other) {
            return other.value >= this.value? -1: 1;
        }

        public Node(int to, int value) {
            Node.this.to = to;
            Node.this.value = value;
        }

    }

}
