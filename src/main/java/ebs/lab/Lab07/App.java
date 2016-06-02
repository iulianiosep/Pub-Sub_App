package ebs.lab.Lab07;

import java.util.Random;

import org.apache.storm.shade.com.google.common.collect.ImmutableList;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.Filter;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.Debug;
import storm.trident.operation.builtin.FilterNull;
import storm.trident.operation.builtin.MapGet;
import storm.trident.operation.builtin.Sum;
import storm.trident.testing.FeederBatchSpout;
import storm.trident.testing.MemoryMapState;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        TridentTopology topology = new TridentTopology();
        FeederBatchSpout spout = new FeederBatchSpout(ImmutableList.of("from","to"));
        
        TridentState travel_count = topology.newStream("travel_agenda", spout)
        		.each(new Fields("from", "to"),new CreateTravelRoute(), new Fields("route"))
                .groupBy(new Fields("route"))
                .persistentAggregate(new MemoryMapState.Factory(), new Count(), new Fields("count"));
        
        LocalDRPC drpc = new LocalDRPC();
        // DRPCClient client = new DRPCClient("localhost", 3772);
        
        topology.newDRPCStream("travel_count", drpc)
        	.stateQuery(travel_count, new Fields("args"), new MapGet(), new Fields("count"));
        
        topology.newDRPCStream("travel_history_count", drpc)
        	.each(new Fields("args"), new SplitRoutes(), new Fields("route"))
        	.groupBy(new Fields("route"))
        	.stateQuery(travel_count, new Fields("route"), new MapGet(), new Fields("count"))
        	.each(new Fields("route", "count"), new Debug())
        	.each(new Fields("count"), new FilterNull())
        	.aggregate(new Fields("count"), new Sum(), new Fields("sum"));
        
        Config conf = new Config();
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("trident", conf, topology.build());
        
        Random random = new Random();
        
        for(int i=0; i<10; i++) {
        	if (random.nextBoolean()) spout.feed(ImmutableList.of(new Values("Iasi", "London")));
        	if (random.nextBoolean()) spout.feed(ImmutableList.of(new Values("Iasi", "Paris")));
        	if (random.nextBoolean()) spout.feed(ImmutableList.of(new Values("Iasi", "Rome")));
         }
       
        System.out.println(drpc.execute("travel_count","Iasi-London"));
        System.out.println(drpc.execute("travel_history_count", "Iasi-London;Iasi-Rome"));
        System.out.println("DONE!!!!!");

        cluster.shutdown();
        drpc.shutdown();
    }
}