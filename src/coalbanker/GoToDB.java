package coalbanker;

import java.util.concurrent.ThreadLocalRandom;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Hud;
import org.powerbot.script.rt6.Item;
import org.powerbot.script.rt6.Npc;
import org.powerbot.script.rt6.TilePath;

public class GoToDB extends Task<ClientContext>{

	public static TilePath pathToDB, pathToCoals;
	private int rockId[] = {436, 437, 438, 439};
	private int ironId[] = {440};
	
	public static final Tile[] PATH = {
			new Tile(3092, 3499, 0),
			new Tile(3084, 3499, 0),
			new Tile(3076, 3500, 0),
			new Tile(3072, 3490, 0),
			new Tile(3072, 3483, 0),
			new Tile(3073, 3475, 0),
			new Tile(3074, 3468, 0),
			new Tile(3073, 3462, 0),
			new Tile(3072, 3453, 0),
			new Tile(3070, 3444, 0),
			new Tile(3070, 3435, 0),
			new Tile(3076, 3433, 0),
			new Tile(3078, 3429, 0),
			new Tile(3082, 3422, 0)
    };
	
	public GoToDB(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().count() == 28
		&& (ctx.depositBox.nearest().tile().distanceTo(ctx.players.local()) > 3);
		
	}

	@Override
	public void execute() {
		CoalBanker.stateofplayer = "Going to Bank";
		CoalBanker.statesignal = "goingtoDB";
		System.out.println("[xzs] " + CoalBanker.statesignal);
		pathToDB = new TilePath(ctx, PATH).reverse();
		pathToDB.traverse();
		if (ctx.bank.nearest().tile().distanceTo(ctx.players.local()) < 10) {
			CoalBanker.statesignal = "idleatDB";
			System.out.println("[xzs] " + CoalBanker.statesignal);
		}
		
		else {
			CoalBanker.statesignal = "goingtoDB";
			System.out.println("[xzs] " + CoalBanker.statesignal);
		}
				
		int b = Random.nextInt(2, 9);
		switch(b){
		  case 1: ctx.camera.angle(Random.nextInt(0, 359));
		  case 2: ctx.camera.pitch(Random.nextInt(50, 100));
		  case 3: ctx.camera.angle(Random.nextInt(0, 359));
		  case 4: ctx.input.move(ctx.input.getLocation().x - Random.nextInt(0, 60), ctx.input.getLocation().y + Random.nextInt(0, 70));
		  case 5: 
		  case 6: ctx.input.move(ctx.input.getLocation().x - Random.nextInt(0, 90), ctx.input.getLocation().y + Random.nextInt(0, 90));
		  case 7: 
		  case 8: ctx.camera.angle(Random.nextInt(0, 359));
		  case 9: ctx.camera.pitch(Random.nextInt(0, 50));
		  case 10: ctx.camera.pitch(Random.nextInt(60, 100));
		}
	}

}
