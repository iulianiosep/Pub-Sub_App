package ebs.lab.Lab07;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

public class SplitRoutes extends BaseFunction {

	public void execute(TridentTuple tuple, TridentCollector collector) {
		// TODO Auto-generated method stub
		for(String route: tuple.getString(0).split(";")) {
			if (route!="") {
				collector.emit(new Values(route));
			}
		}

	}

}