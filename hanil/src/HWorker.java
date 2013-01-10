import lempel.blueprint.base.concurrent.JobQueue;
import lempel.blueprint.base.concurrent.Worker;


public class HWorker extends Worker<Hbroker>{

	public HWorker(final JobQueue<Hbroker> jQueue) {
		super(jQueue);
	}

	@Override
	protected void process(Hbroker clientObject) {
		clientObject.exec();
	}

}
