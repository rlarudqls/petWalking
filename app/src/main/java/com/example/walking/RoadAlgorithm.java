package com.example.walking;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Stack;


class Point {
    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    double x;
    double y;
    public static double getDistance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }
}
enum MarkType {
    START,
    MARK,
    END

}
class Vertex {
    Vertex(MarkType type, Point location, ArrayList<Integer> canGo, int index, double good) {
        this.type = type;
        this.location = location;
        this.canGo = canGo; // 갈수 있는 곳
        this.index = index;
        this.good = good;
    }
    public MarkType type;
    public Point location;
    public ArrayList<Integer> canGo;
    public int index;
    public double good;

    @Override
    public String toString() {
        return "Vertex{" +
                "type=" + type +
                ", location=" + location +
                ", canGo=" + canGo +
                ", index=" + index +
                '}';
    }
}
class Node {    //이름 바꾸기
    Node(Vertex vertex, double weight, double distance) {
        this.vertex = vertex;
        this.weight = weight;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Node{" +
                "back=" + back +
                ", vertex=" + vertex +
                ", weight=" + weight +
                ", distance=" + distance +
                '}';
    }

    ArrayList<Integer> back = new ArrayList<>();    //개선 dist 배열로 백트래킹
    Vertex vertex; // 현재위치
    double weight;  //가중치
    double distance; //지금 까지 거리
}

// 최대거리 및 경유지 설정은 MainActivity에서 성정
public class RoadAlgorithm {
    public static ArrayList<Point> TSP(Point start, Point end, ArrayList<Point> inputs) {
        ArrayList<Boolean> visit = new ArrayList<>();
        Stack<Integer> back = new Stack<>();

        minDist = Double.MAX_VALUE;
        for(int i = 0; i < inputs.size(); i++) {
            visit.add(false);
        }
        Point[] aa = new Point[inputs.size()];
        inputs.toArray(aa);
        TSPDFS(-1, start, end, aa, 0, 0, back, visit);
        ArrayList<Point> ret = new ArrayList<>();
        System.out.println(minDist);
        /*int minIndex = -1;
        double minStartDist = Double.MAX_VALUE;
        for(int i = 0; i < result.size(); i++) {
            double dist = Point.getDistance(aa[result.get(i)], start);
            if(minStartDist > dist) {
                minStartDist = dist;
                minIndex = i;
            }
        }
        for(int i = minIndex; i < result.size(); i++) ret.add(aa[result.get(i)]);
        for(int i = 0; i < minIndex; i++) ret.add(aa[result.get(i)]);*/
        if(Point.getDistance(aa[result.get(0)], start) > Point.getDistance(aa[result.get(result.size() - 1)], start))
            Collections.reverse(result);
        for (int i : result) ret.add(aa[i]);
        return ret;
    }

    private static ArrayList<Integer> result;
    private static double minDist;
    private static void TSPDFS(int now, Point start, Point end, Point[] inputs, double dist, int depth, Stack<Integer> back, ArrayList<Boolean> visit) {
        if(depth == inputs.length) {
            dist += Point.getDistance(end, inputs[now]);
            if(dist < minDist) {
                minDist = dist;
                result = new ArrayList<>();
                Stack<Integer> copy = (Stack<Integer>) back.clone();
                while (!copy.isEmpty()) {
                    result.add(copy.pop());
                }
                int size = result.size();
                for (int i = 0; i < size; i++) {
                    int t = result.get(i);
                    int count = size - i - 1;
                    result.set(i, result.get(count));
                    result.set(count, t);
                }
            }
        }
        for(int i = 0; i < inputs.length; i++) {
            if(!visit.get(i)) {
                visit.set(i, true);
                back.push(i);
                double cDist;
                if(now == -1) dist += (cDist = Point.getDistance(start, inputs[i]));
                else dist += (cDist = Point.getDistance(inputs[now], inputs[i]));
                TSPDFS(i, start, end ,inputs, dist, depth + 1, back, visit);
                dist -= cDist;
                back.pop();
                visit.set(i, false);
            }
        }
    }

    public static ArrayList<Integer> run(Point[] inputs,  double limit, int countPath) {

        countPath = Math.min(countPath, inputs.length - 2); // 예외 : inputs 개수 부족

        // 자바 우선큐
        PriorityQueue<Node> PQ = new PriorityQueue<Node>(10, (o1, o2) -> {
            return (int) (o1.weight - o2.weight);
        });
        // https://namu.wiki/w/A*%20%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98


        int inputLength = inputs.length;
        ArrayList<Vertex> vertexes = new ArrayList<>();
        for(int i = 0; i < inputLength; i++) {

            ArrayList<Integer> canGo = new ArrayList<>();
            double score = 0;
            for(int j = 1; j < inputLength - 1; j++) {

                if(i == j) continue;
                //System.out.println("거리: " + Point.getDistance(inputs[i], inputs[j]));
                if(Point.getDistance(inputs[i], inputs[j]) < limit) score -= 0.0115;  //밀집도 가중치
                if(((int)(Math.random() * 4)) == 1) continue;
                canGo.add(j);
                //
            }
            MarkType type = MarkType.MARK;
            if(i == 0) type = MarkType.START;
            else if(i == inputLength - 1) type = MarkType.END;

            Vertex vertex = new Vertex(type, inputs[i], canGo, i, score);
            vertexes.add(vertex);
        }

        // https://namu.wiki/w/A*%20%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98
        PQ.add(new Node(vertexes.get(0), 0, 0));       //우선순위 큐에 시작 노드를 삽입한다.

        while(!PQ.isEmpty()) {
            Node now = PQ.poll();     //우선순위 큐를 pop한다.
            if (now.back.size() > countPath) {      //경유지 갯수 초과시 종료
                now.back.add(inputLength - 1);
                return now.back;    //정답
            }
            // 인접 버텍스 방문

            for (int nextIndex : now.vertex.canGo) {    //해당 노드에서 이동할 수 있는 다음 노드들을 보는 동안
                boolean isContinue = false;
                for(int a : now.back) if(a == nextIndex) isContinue = true;  // visit
                if(isContinue) continue;
                Vertex next = vertexes.get(nextIndex);


                // f(x)=g(x)+h(x)
                double G = now.distance;
                double nextDistance = now.distance + Point.getDistance(now.vertex.location, next.location);

                // 높을수록 가까운데로 간다 , 너무 높은면 가까운 데로만 간다
                // 수시로 조절 및 테스트 필요할듯
                double H = Point.getDistance(next.location, vertexes.get(inputLength - 1).location) + next.good;

                Node nextNode = new Node(next, G + H, nextDistance); //해당 노드에서 이동할 수 있는 다음 노드들을 보는 동안
                nextNode.back = (ArrayList<Integer>) now.back.clone();
                nextNode.back.add(now.vertex.index);
                PQ.add(nextNode);       //우선순위 큐에 다음 노드를 삽입한다.
            }
        };
        return null;
    }

}
