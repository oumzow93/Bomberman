package objets;

import agent.Position;

public class Item {
	@Override
	public String toString() {
		return  p + "," + typeItem ;
	}
	private Position p;
	private int typeItem;
	
	public Item(Position p, int typeItem) {
		super();
		this.setP(p);
		this.setTypeItem(typeItem);
	}
	public Position getP() {
		return p;
	}
	public void setP(Position p) {
		this.p = p;
	}
	public int getTypeItem() {
		return typeItem;
	}
	public void setTypeItem(int typeItem) {
		this.typeItem = typeItem;
	}
	


}
