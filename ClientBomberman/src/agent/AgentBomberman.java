package agent;



public class AgentBomberman  extends Agent{
	private int couleur;
	static int compteur=0;
	private boolean estInvincible;
	private boolean peutPoserBomb;
	private boolean estMalade;
	private int rangeBom;
	public  static int nombre_de_vies=5;

	public AgentBomberman(Position poistion) {
		super(poistion);
		// TODO Auto-generated constructor stub
		this.setCouleur(compteur++);
		this.estInvincible=false;
		this.estMalade=false;
		this.peutPoserBomb=true;
		this.rangeBom=1;
	}

	@Override
	public char typeAgent() {
		// TODO Auto-generated method stub
		return 'B';
	}

	public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}
	
	public static void  setIdCouleur() {
		compteur=0;
	}

	public boolean isEstMalade() {
		return estMalade;
	}

		public void setEstMalade(boolean m) {
		this.estMalade= m;
	
	}

	public boolean isInvincible() {
		return estInvincible;
	}

	public void setinvincible(boolean i) {
		this.estInvincible=i;
			
	}

	public boolean isPeutPoserBomb() {
		return peutPoserBomb;
	}

	public void setPeutPoserBomb(boolean peutPoserBomb) {
		this.peutPoserBomb = peutPoserBomb;
	}

	public int getRangeBom() {
		return rangeBom;
	}


	public void AugmentRange() {
		this.rangeBom++;
	}
	public void DimunueRnage() {
		if(this.rangeBom>1) {
			this.rangeBom--;
		}
	}


}
