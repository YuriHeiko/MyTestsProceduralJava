package ThreadStates;

public class LoggingStateThread extends Thread {
    private State state;
    private Thread target;
//    HashSet<State> map = new HashSet<>();

    public LoggingStateThread(Thread target) {
        this.target = target;
        state = target.getState();
//        setDaemon(true);
    }

    @Override
    public void run() {
        Solution.map.put(state, 1L);
        state = target.getState();
        while (state != State.TERMINATED) {
            if (state != State.TERMINATED) {
                state = target.getState();
                if (Solution.map.containsKey(state))
                    Solution.map.put(state, Solution.map.get(state) + 1);
                else
                    Solution.map.put(state, 1L);
            }
        }
/*
        System.out.println(state);
        State state = target.getState();
        System.out.println(target.getState());
        while (state != State.TERMINATED) {
            if (state != target.getState()) {
                state = target.getState();
                System.out.println(state);
            }
        }
*/
    }
}
