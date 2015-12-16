package coalbanker;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.DepositBox.Amount;
import org.powerbot.script.rt6.Item;
import org.powerbot.script.rt6.Npc;
import org.powerbot.script.rt6.Widget;

public class Bank extends Task<ClientContext>{

	private int[] uncutId = {1617, 1618, 1619, 1620, 1621, 1622, 1623, 1624, 1625, 1626, 1627, 1628, 1629, 1630, 1631, 1632};
	public Bank(ClientContext ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().count() > 1
				&& (ctx.depositBox.nearest().tile().distanceTo(ctx.players.local()) < 3);
	}

    @Override
	public void execute() {
		CoalBanker.statesignal = "banking";
		System.out.println("[xzs] " + CoalBanker.statesignal);
		ctx.depositBox.open();
		if (ctx.depositBox.opened()) {
		ctx.depositBox.deposit(453, Amount.ALL);
		for(Item i : ctx.backpack.id(uncutId)) {
		    i.interact("Deposit");
		}
		
		}
		else if (!ctx.depositBox.opened()) {
			ctx.depositBox.open();
		}
		if (ctx.backpack.select().count() < 28) {
			ctx.depositBox.close();
			CoalBanker.statesignal = "banked";
			System.out.println("[xzs] " + CoalBanker.statesignal);
		}
		else {
			CoalBanker.statesignal = "waiting to bank";
			System.out.println("[xzs] " + CoalBanker.statesignal);
		}
    }	

}
