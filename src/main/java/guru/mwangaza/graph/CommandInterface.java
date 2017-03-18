package guru.mwangaza.graph;


public interface CommandInterface<T> {
	public void execute(Node<T> node);
}
