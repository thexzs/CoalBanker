package coalbanker;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.GroundItem;
import org.powerbot.script.rt6.GroundItems;
import org.powerbot.script.rt6.Hud;
import org.powerbot.script.rt6.Players;
import org.powerbot.script.rt6.RelativeLocation;
import org.powerbot.script.rt6.TilePath;

import java.util.concurrent.ThreadLocalRandom;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Locatable;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;

public class Mine extends Task<ClientContext> {

	public static final int[] ctrockIds = {11959, 11957, 11962, 11960, 11933, 11934, 11935};
	public static final int[] irockIds = {11954, 11955, 11956, 14856, 14857, 14858, 14913, 14914, 19000, 19001, 19002, 5773, 5774, 5775, 6943, 6944, 2092, 2093};
	private int[] otherRocks = {11555, 11955, 11557, 11930, 11931, 11932};
		
	public Mine(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
				return ctx.backpack.select().count() < 28
				&& !ctx.objects.select().name("Coal rocks").isEmpty()
				&& !(ctx.players.local().animation() == 625)
				&& !(ctx.players.local().inMotion());
		}

	private int rand = 0;

	int x1 = ThreadLocalRandom.current().nextInt(10, 40);
	int y1 = ThreadLocalRandom.current().nextInt(10, 40);
	
	@Override
	public void execute() {
		CoalBanker.stateofplayer = "Mining";
		CoalBanker.statesignal = "clicked on Mine";
		System.out.println("[xzs] " + CoalBanker.statesignal);
		GameObject rock = ctx.objects.nearest().poll();
		if(rock.inViewport()) {
			if (!ctx.hud.opened(Hud.Window.BACKPACK)){
				ctx.hud.opened(Hud.Window.BACKPACK);
			}
			rock.interact("Mine");
			ctx.input.move(ctx.input.getLocation().x - x1, ctx.input.getLocation().y + y1);
			rand = ThreadLocalRandom.current().nextInt(200, 300);
			Condition.sleep(rand);
			int b = Random.nextInt(1, 10);
			switch(b){
			  case 1: ctx.camera.angle(Random.nextInt(0, 359));
			  case 2: ctx.camera.pitch(Random.nextInt(50, 100));
			  case 3: ctx.camera.angle(Random.nextInt(0, 359));
			  case 4: ctx.input.move(ctx.input.getLocation().x - Random.nextInt(0, 30), ctx.input.getLocation().y + Random.nextInt(0, 40));
			  case 5: 
			  case 6: ctx.input.move(ctx.input.getLocation().x - Random.nextInt(0, 50), ctx.input.getLocation().y + Random.nextInt(0, 90));
			  case 7: 
			  case 8: ctx.camera.angle(Random.nextInt(0, 359));
			  case 9: ctx.camera.pitch(Random.nextInt(0, 50));
			  case 10: ctx.camera.pitch(Random.nextInt(60, 100));
			}
		}
		else
		{
			rand = ThreadLocalRandom.current().nextInt(100, 200);
			Condition.sleep(rand);
			ctx.camera.turnTo(rock);
			ctx.movement.step(rock);
			Condition.sleep(rand);
		}
	}

}
