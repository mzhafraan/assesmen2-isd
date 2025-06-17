import java.util.*;

class BurgerGraph {
    private LinkedList<String> steps;
    private LinkedList<LinkedList<String>> adjacencyList;

    public BurgerGraph() {
        this.steps = new LinkedList<>();
        this.adjacencyList = new LinkedList<>();
    }

    public void addStep(String step) {
        if (!steps.contains(step)) {
            steps.add(step);
            adjacencyList.add(new LinkedList<>());
        }
    }

    private int getStepIndex(String step) {
        return steps.indexOf(step);
    }

    public void addConnection(String step1, String step2) {
        addStep(step1);
        addStep(step2);

        int index1 = getStepIndex(step1);
        int index2 = getStepIndex(step2);

        if (index1 != -1 && index2 != -1) {
            adjacencyList.get(index1).add(step2);
            adjacencyList.get(index2).add(step1);
        }
    }

    public LinkedList<String> getNeighbors(String step) {
        int index = getStepIndex(step);
        if (index != -1) {
            return adjacencyList.get(index);
        }
        return new LinkedList<>();
    }

    public LinkedList<String> bfsTraversal(String startStep) {
        LinkedList<String> visitedOrder = new LinkedList<>();
        LinkedList<String> queue = new LinkedList<>();
        LinkedList<String> visited = new LinkedList<>();

        if (!steps.contains(startStep)) {
            System.out.println("Vertex " + startStep + " tidak ditemukan!");
            return visitedOrder;
        }

        queue.addLast(startStep);
        visited.add(startStep);

        while (!queue.isEmpty()) {
            String currentStep = queue.removeFirst();
            visitedOrder.addLast(currentStep);

            LinkedList<String> neighbors = getNeighbors(currentStep);
            Collections.sort(neighbors);

            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.addLast(neighbor);
                }
            }
        }
        return visitedOrder;
    }

    public LinkedList<String> dfsTraversal(String startStep) {
    LinkedList<String> visitedOrder = new LinkedList<>();
    LinkedList<String> stack = new LinkedList<>();
    LinkedList<String> visited = new LinkedList<>();

    if (!steps.contains(startStep)) {
        return visitedOrder;
    }

    stack.addFirst(startStep);

    while (!stack.isEmpty()) {
        String currentStep = stack.removeFirst();

        if (!visited.contains(currentStep)) {
            visited.add(currentStep);
            visitedOrder.addLast(currentStep);


            LinkedList<String> neighbors = getNeighbors(currentStep);
            Collections.sort(neighbors, Collections.reverseOrder());

            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    stack.addFirst(neighbor);
                }
            }
        }
    }
    return visitedOrder;
}
}

public class Main {
    public static void main(String[] args) {

        BurgerGraph burgerStep = new BurgerGraph();

        String[] steps = {
            "preheat oven", "bake bread", "serve bread",
            "set plate", "add some pickles", "add krabby patty",
            "serve patty", "add tartar sauce", "pour sauce over patty",
            "eat", "preheat pan", "add some pickles",
        };
        
        // System.out.println("Menambahkan Vertex:");
        // for (String step : steps) {
        //     ukMap.addStep(step);
        //     System.out.println("- " + step);
        // }

        // System.out.println("\nMenambahkan jalur antar vertex:");
        burgerStep.addConnection("preheat oven", "bake bread");
        burgerStep.addConnection("preheat oven", "preheat pan");

        burgerStep.addConnection("preheat pan", "set plate");
        burgerStep.addConnection("preheat pan", "add krabby patty");

        burgerStep.addConnection("set plate", "serve bread");
        burgerStep.addConnection("set plate", "add some pickles");
        burgerStep.addConnection("set plate", "serve patty");

        burgerStep.addConnection("serve bread", "eat");

        burgerStep.addConnection("add some pickles", "eat");

        burgerStep.addConnection("add krabby patty", "serve patty");
        burgerStep.addConnection("add krabby patty", "add tartar sauce");

        burgerStep.addConnection("add tartar sauce", "pour sauce over patty");

        burgerStep.addConnection("pour sauce over patty", "eat");

        String startStep = "preheat oven";
        System.out.println("Perencanaan Perjalanan Dira");
        System.out.println("Step Awal: " + startStep);

        System.out.println("(BFS)");
        LinkedList<String> bfsResult = burgerStep.bfsTraversal(startStep);
        System.out.println("\nStep BFS:");
        printRoute(bfsResult);

        System.out.println("\n(DFS)");
        LinkedList<String> dfsResult = burgerStep.dfsTraversal(startStep);
        System.out.println("\nStep DFS:");
        printRoute(dfsResult);
        

    }
    
    private static void printRoute(LinkedList<String> route) {
        if (route.isEmpty()) {
            System.out.println("Step tidak dapat dibuat.");
            return;
        }
        
        System.out.print("  ");
        for (int i = 0; i < route.size(); i++) {
            System.out.print(route.get(i));
            if (i < route.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
        // System.out.println("📊 Total kota dikunjungi: " + route.size());
    }
}