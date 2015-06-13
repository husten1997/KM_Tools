package gen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


import gen.generator.*;



public class WorldGen {
	
	static final String RAND = "RAND";
	static final String RAND_DQ = "RAND_DQ";
	static final String RAND_DD = "RAND_DD";
	static final String SIN = "SIN";
	
	float max;
	float min;
	float dif;
	float routh;
	float fallof;
	float c = 2f;
	int smoothS;
	
	float[][] hm;
	
	int s = 512+1;
	BufferedImage HM = new BufferedImage(s, s,BufferedImage.TYPE_INT_ARGB);
	BufferedImage CM = new BufferedImage(s, s,BufferedImage.TYPE_INT_RGB);
	static int seed;
	Random rand; 
	
	
	/**
	 * Warscheinlichkeiten!!
	 */
	double WWC;
	double WSC;
	double WGC;
	double WRC;
	double WMC;
	
	double WER = 0.07;
	double WKR = 0.12;
	
	Color wasser = new Color(29, 60, 213);
	Color sand = new Color(224, 163, 0);
	Color gras = new Color(44, 152, 15);
	Color rock = new Color(78, 78, 78);
	Color eisen = new Color(100, 0, 0);
	Color kohle = new Color(1, 1, 1);
	Color schnee = new Color(255, 255, 255);
	
	Generator generator;
	
	public WorldGen(){
		WWC = 0.35;
		WSC = 0.5;
		WGC = 0.8;
		WRC = 1;
		WMC = 0;
		
		routh = 0.6f;
		fallof = 0.9f;
		smoothS = 25;
	}
	
	public WorldGen(double WWL, double WSL, double WGL, double WRL, float routh, float fall, int smoothS){
		WWC = WWL;
		WSC = WSL;
		WGC = WGL;
		WRC = WRL;
		this.routh = routh;
		fallof = fall;
		this.smoothS = smoothS;

	}
	public static void main(String[] args) {
		WorldGen wg = new WorldGen();
		wg.gen();
	}
	
	public void setLevel(double WWL, double WSL, double WGL, double WRL){
		WWC = WWL;
		WSC = WSL;
		WGC = WGL;
		WRC = WRL;
	}
	
	
	
	public void setWWC(double wWC) {
		WWC = wWC;
		trans();
	}

	public void setWSC(double wSC) {
		WSC = wSC;
		trans();
	}

	public void setWGC(double wGC) {
		WGC = wGC;
		trans();
	}

	public void setWRC(double wRC) {
		WRC = wRC;
		trans();
	}

	public void setWMC(double wMC) {
		WMC = wMC;
		trans();
	}

	public void gen(){
		seed = (int)(Math.random()*Integer.MAX_VALUE);
		hm = new float[s][s];
		rand = new Random(seed);
		generator = new RandomGenerator(rand, c, -1);
		long Zvor = System.currentTimeMillis();
		genHM2(seed, routh);
		set();
		trans();
		long Znach = System.currentTimeMillis();
		long time = Znach-Zvor;
		System.out.println("Done in: " + time + " ms");
		
	}
	
	public void gen(int Iseed){
		seed = Iseed;
		hm = new float[s][s];
		rand = new Random(seed);
		generator = new RandomGenerator(rand, c, -1);
		long Zvor = System.currentTimeMillis();
		genHM2(seed, routh);
		set();
		trans();
		long Znach = System.currentTimeMillis();
		long time = Znach-Zvor;
		System.out.println("Done in: " + time + " ms");
		
		
	}
	
	public void genHM(int seed, float r){
		Random rand = new Random(seed);
		int size = (int) Math.sqrt(s-1);
		int depth = size -1;
		hm[0][0] = r * (2*rand.nextFloat()-1);
		hm[0][size-1] = r * (2*rand.nextFloat()-1);
		hm[size-1][0] = r * (2*rand.nextFloat()-1);
		hm[size-1][size-1] = r * (2*rand.nextFloat()-1);
		
		int inter;
		boolean putX, putZ;
		while(depth > -1){
			inter = (int) Math.round(Math.pow(2, depth));
			putX = false;
			for(int i = 0; i < hm.length; i+= inter){
				putZ = false;
				for(int j = 0; j < hm[0].length; j += inter){
					try{
						if(putX && putZ){
							hm[i][j] = (hm[i - inter][j - inter] +
										hm[i + inter][j - inter] +
										hm[i - inter][j + inter] +
										hm[i + inter][j + inter])/4
										+ r * (2*rand.nextFloat()-1);
						}
						if(putX != putZ){
							if(putX){
								hm[i][j] = (hm[i - inter][j] + 
											hm[i + inter][j])/2
											+ r * (2*rand.nextFloat()-1);
							} else{
								hm[i][j] = (hm[i][j - inter] + 
											hm[i][j + inter])/2
											+ r * (2*rand.nextFloat()-1);
							}
						}
						putZ = !putZ;
					}catch(Exception e){
						
					}
					
				}
				putX = !putX;
			}
			r /= 2;
			depth--;
		}
		for(int i = 0; i < hm.length; i++){
			for(int j = 0; j < hm.length; j++){
				hm[i][j] = (float)(hm[i][j] <= 0? -Math.sqrt(Math.abs(hm[i][j])) : hm[i][j]);
			}
		}
		
	}
	
	public void genHM2( int seed, float r){
//		int size = (int) Math.sqrt(s-1);
		int inter = Math.round(s/2);
		square(inter+1, inter+1, inter, r);
		smooth(smoothS);	
	}
	
	public void set(){
		min = hm[1][1];
		max = hm[1][1];
		for(int i = 0; i < hm.length; i++){
			
			for(int j = 0; j < hm.length; j++){
				min = (hm[i][j] < min ? hm[i][j]:min);
				max = (hm[i][j] > max ? hm[i][j]:max);
				
			}
			
		}

		dif = max - min;

		float value;
		float minA = hm[1][1] +(0 - min)/(dif);
		float maxA = hm[1][1] +(0 - min)/(dif);
		

		
		for(int c = 0; c < hm.length; c++){
			
			for(int d = 0; d < hm.length; d++){

				value = (hm[c][d] +(0 - min))/dif;
				minA = (value < minA ? value : minA);
				maxA = (value > maxA ? value : maxA);
				HM.setRGB(c, s-1-d, new Color(0, 0, 0, ((hm[c][d] +(0 - min))/dif)).getRGB());
				
				
			}
			
		}

		
		try {
			File output = new File("C:/output/HM.png");
			
			output.createNewFile();
			ImageIO.write(HM, "png", output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		
	}
	
	public void square(int xPos, int yPos, int inter, float r){

		if(xPos < s && xPos >= 0 && yPos < s && yPos >= 0){
			if(hm[xPos][yPos] == 0.0){
//					hm[xPos][yPos] = dsquare(xPos, yPos, inter)+ r * (c*rand.nextFloat()-1);
					hm[xPos][yPos] = generator.getValue(xPos, yPos, r, dsquare(xPos, yPos, inter));
			}
			

			diamond(xPos + inter, yPos, inter, r);
			diamond(xPos, yPos + inter, inter, r);
			diamond(xPos - inter, yPos, inter, r);
			diamond(xPos, yPos- inter, inter, r);
			
		}
		
		
		
	}
	
	public void diamond(int xPos, int yPos, int inter, float r){
		if(inter >= 1 && xPos < s && xPos >= 0 && yPos < s && yPos >= 0){
			if(hm[xPos][yPos] == 0.0){
//				hm[xPos][yPos] = ddia(xPos, yPos, inter) + r * (c*rand.nextFloat()-1) ;
				hm[xPos][yPos] = generator.getValue(xPos, yPos, r, ddia(xPos, yPos, inter));
				Vektor M1 = middl(new Vektor((double)(xPos + inter),(double) (yPos)), new Vektor((double)(xPos), (double)(yPos + inter)));

				square((int)M1.getX() + (inter/2), (int)M1.getY() + (inter/2), (int) Math.round(inter/2), r*fallof);

				

				Vektor M2 = middl(new Vektor((double)(xPos - inter), (double)(yPos)), new Vektor((double)(xPos), (double)(yPos + inter)));

				square((int)M2.getX() + (inter/2), (int)M2.getY() + (inter/2), (int) Math.round(inter/2), r*fallof);

				

				Vektor M3 = middl(new Vektor((double)(xPos + inter), (double)(yPos)), new Vektor((double)(xPos), (double)(yPos - inter)));

				square((int)M3.getX() + (inter/2), (int)M3.getY() + (inter/2), (int) Math.round(inter/2), r*fallof);

				Vektor M4 = middl(new Vektor((double)(xPos - inter), (double)(yPos)), new Vektor((double)(xPos), (double)(yPos - inter)));

				square((int)M4.getX() + (inter/2), (int)M4.getY() + (inter/2), (int) Math.round(inter/2), r*fallof);
			}
		}else{

		}
		
		
		
	}
	
	public Vektor middl(Vektor v1, Vektor v2){
		double x1 = (v1.getX() + v2.getX())/2;
		double y1 = (v1.getY() + v2.getY())/2;
		return new Vektor(x1, y1);
	}
	
	public float ddia(int xPos, int yPos, int inter){
		int blub = 0;
		float value = 0;
		if(xPos + inter < s){
			value += hm[xPos + inter][yPos];
			blub++;
		}
		if(xPos - inter > 0){
			value += hm[xPos - inter][yPos];
			blub++;
		}
		if(yPos + inter < s){
			value += hm[xPos][yPos + inter];
			blub++;
		}
		if(yPos - inter > 0){
			value += hm[xPos][yPos - inter];
			blub++;
		}
		return value/blub;
	}
	
	public float dsquare(int xPos, int yPos, int inter){
		int blub = 0;
		float value = 0;
		if(xPos + inter < s && yPos + inter < s){
			value += hm[xPos + inter][yPos + inter];
			blub++;
		}
		if(xPos - inter >= 0 && yPos - inter >= 0){
			value += hm[xPos - inter][yPos - inter];
			blub++;
		}
		if(xPos - inter >= 0 && yPos + inter < s){
			value += hm[xPos - inter][yPos + inter];
			blub++;
		}
		if(xPos + inter < s && yPos - inter >= 0){
			value += hm[xPos + inter][yPos - inter];
			blub++;
		}
		return value/blub;
	}
	
	public void smooth(int am){
		for(int s = 0; s < am; s++){
			for(int i = 0; i < hm.length; i++){
				for(int j = 0; j < hm.length; j++){
					hm[i][j] = ddia(i, j, 1);
				}
			}
		}
		
		

	}
	
	public void trans(){
		double WW = min + (WWC * dif);
		double WS = min + (WSC * dif);
		double WG = min + (WGC * dif);
		double WR = min + (WRC * dif);

		
		for(int i = 0; i < hm.length; i++){
			Random randomRes = new Random(rand.nextLong());
			for(int j = 0; j < hm.length; j++){
				float z = hm[i][j];
				
				if(z < WW){
					CM.setRGB(i, s-1-j, wasser.getRGB());
				}
				if(z > WW && z < WS){
					CM.setRGB(i, s-1-j, sand.getRGB());
				}
				if(z > WS && z < WG){
					CM.setRGB(i, s-1-j, gras.getRGB());
				}
				if(z > WG && z < WR){
					CM.setRGB(i, s-1-j, rock.getRGB());
					int x = randomRes.nextInt(100);
					if(x < WER*100){
						CM.setRGB(i,  s-1-j, eisen.getRGB());
					}
					if(x < WKR*100+WER*100 && x > WER*100){
						CM.setRGB(i, s-1-j, kohle.getRGB());
					}
				}
				if((z > WR)){
					CM.setRGB(i, s-1-j, schnee.getRGB());
				}
				
			}
			
		}
		
		try {
			File output = new File("C:/output/CM.png");
			
			output.createNewFile();
			ImageIO.write(CM, "png", output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}
	
	

}
