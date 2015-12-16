package coalbanker;

import org.powerbot.bot.rt6.client.Player;
import org.powerbot.bot.rt6.client.Skill;
import org.powerbot.script.Locatable;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Constants;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Hiscores.SkillStats;
import org.powerbot.script.rt6.Players;
import org.powerbot.script.rt6.Skills;
import org.powerbot.script.rt6.TilePath;
import org.powerbot.script.Script;
import org.powerbot.script.Tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Script.Manifest(name="Coal Banker " + CoalBanker.Versionz, description="Coals from Gunnarsgrunn to Edgeville.")
public class CoalBanker extends PollingScript<ClientContext> implements MessageListener, PaintListener {

	public static String stateofplayer;
	public static String statesignal = "idle";
	public static final String Versionz = "Beta 1.7";
	private int startLvl = ctx.skills.level(Constants.SKILLS_MINING);
	private int startExp = ctx.skills.experience(Constants.SKILLS_MINING);
	private List<Task> taskList = new ArrayList<Task>();
					
	@Override
    public void start() {
		taskList.addAll(Arrays.asList(new Mine(ctx), new GoToDB(ctx), new GoToCoals(ctx), new Bank(ctx)));
		System.out.println("[xzs] Script started. " + System.currentTimeMillis());
    }

    @Override
    public void poll() {
    	for (Task task : taskList) {
            if (task.activate()) {
                task.execute();
            }
        }
    }

	public static int mousenowy; 
	@Override
	public void repaint(Graphics g) {
		
		Font font = new Font("Verdana", Font.BOLD, 14);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawRect(6, 99, 218, 128);
		Color cyanish = new Color(122, 7, 7, 120);
		g.setColor(cyanish);
		g.fillRect(7, 100, 216, 126);
		g.setColor(Color.white);
		g.drawString("Coal Banker by xzs", 10, 115);
		g.drawLine(12, 118, 214, 118);
		Font font2 = new Font("Verdana", 0, 12);
		g.setFont(font2);
		g.drawString("Player status: " + stateofplayer, 10, 132);
		int expGained = ctx.skills.experience(Constants.SKILLS_MINING) - startExp;
		g.drawString("Mining Exp Gained: " + expGained, 10, 145);
		int lvlGained = ctx.skills.level(Constants.SKILLS_MINING) - startLvl;
		g.drawString("Mining Level Gained: " + lvlGained + "(" + ctx.skills.level(Constants.SKILLS_MINING) + ")", 10, 158);
		int skill = Constants.SKILLS_MINING;
		int expToNext = ctx.skills.experienceAt(ctx.skills.realLevel(skill) + 1) - ctx.skills.experience(skill);
		g.drawString("EXP to Next Level: " + expToNext, 10, 171);
		int timeelapseint = (int) getRuntime() / 1000;
		g.drawString("Total time running: " + timeelapseint + "s.", 10, 184);
		int expPhr = expGained * 360000;
		int timeinint = (int) getRuntime();
		g.drawString("EXP Per Hour: " + (expPhr / timeinint) + " xp/hr.", 10, 197);
		g.drawString("Total Coals Mined: " + mined + ".", 10, 210);
		g.drawString("Script Version " + Versionz, 10, 223); 

		Point mousenow = ctx.input.getLocation();
		mousenowy = mousenow.y + 100;
		Graphics2D g2 = (Graphics2D) g;
		int mousenowx = mousenow.x;
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(mousenowx - 1000, mousenowy, mousenowx + 1000, mousenowy);
		g2.drawLine(mousenowx,  mousenowy - 1000,  mousenowx,  mousenowy +1000);
		
		Graphics2D g3 = (Graphics2D) g;
		g3.setColor(Color.RED);
		g3.setStroke(new BasicStroke(1));
		g3.drawLine(mousenowx - 1000, mousenowy, mousenowx + 1000, mousenowy);
		g3.drawLine(mousenowx,  mousenowy - 1000,  mousenowx,  mousenowy +1000);
		
	}

	public int mined = 0;
	public int miningexp = 0;
	private int oreexp = 18;
	@Override
	public void messaged(MessageEvent m) {
		String msg = m.text().toString();
		if(msg.contains("You manage to mine")) {
		mined++;
		miningexp = miningexp + oreexp;
		System.out.println("[xzs] +1 ore");
		}
	
	}
    
}
