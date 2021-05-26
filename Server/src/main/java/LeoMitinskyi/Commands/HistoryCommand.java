package LeoMitinskyi.Commands;

import LeoMitinskyi.Engine;
import LeoMitinskyi.ServerLoop;

import java.util.List;
import java.util.ListIterator;

public class HistoryCommand extends Command {
    public HistoryCommand(Engine engine, ServerLoop serverLoop) {
        super(engine,serverLoop);
    }
    @Override
    public String execute(){
        List<String> history = engine.getHistory();
        int startIndex = Math.max(0, history.size() - 12);
        ListIterator<String> iterator = history.listIterator(startIndex);
        StringBuilder historySearching = new StringBuilder();
        while (iterator.hasNext()) {
            historySearching.append(iterator.next()).append('\n');
        }
        return historySearching.toString();
    }

    @Override
    public String getDescription() {
        return ":вывести последние 12 команд (без их аргументов)";
    }

    @Override
    public String getName() {
        return "history";
    }
}