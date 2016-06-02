package ebs.lab.Lab07;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

public class CreateTravelRoute extends BaseFunction {

	public void execute(TridentTuple tuple, TridentCollector collector) {
		// TODO Auto-generated method stub
		String from = tuple.getString(0);
		String to = tuple.getString(1);
		collector.emit(new Values(from+"-"+to));
	}

}