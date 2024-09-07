package makima.command;

public class FindState extends State {
    @Override
    public String getResponse(String input, Makima makima) {
        makima.setState(new WaitingState());
        return makima.getTasksWithTerm(input);
    }
}
