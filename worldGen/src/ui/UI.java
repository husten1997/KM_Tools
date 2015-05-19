package ui;

import gen.WorldGen;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UI extends JFrame {

	private JPanel contentPane;
	private JTextField et_seed;
	
	static final String button_rand = "Random";
	
	static WorldGen gen;
	PicturePanel panel;
	
	static final String Version = "a0.1";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		gen = new WorldGen();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setTitle("WorldGen " + Version);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		et_seed = new JTextField();
		et_seed.setColumns(10);
		
		JButton btnRandome = new JButton(button_rand);
		btnRandome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double rand = Math.random()*Integer.MAX_VALUE;
				et_seed.setText("" + (int)rand);
			}
		});
		
		JLabel lblSeed = new JLabel("Seed");
		
		JLabel lblWaterLevel = new JLabel("Water Level");
		
		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				lblWaterLevel.setText("Water Level: " + slider.getValue());
				gen.setWWC((double)(slider.getValue())/100);
				update();
				
			}
		});
		
		JLabel lblSandLevel = new JLabel("Sand Level");
		
		JSlider slider_1 = new JSlider();
		slider_1.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				lblSandLevel.setText("Sand Level: " + slider_1.getValue());
				slider.setMaximum(slider_1.getValue());
				gen.setWSC((double)(slider_1.getValue())/100);
				update();
				
			}
		});
		
		JLabel lblGrasLevel = new JLabel("Gras Level");
		
		JSlider slider_2 = new JSlider();
		slider_2.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				lblGrasLevel.setText("Gras Level: " + slider_2.getValue());
				slider_1.setMaximum(slider_2.getValue());
				gen.setWGC((double)(slider_2.getValue())/100);
				update();
				
			}
		});
		
		JLabel lblRockLevel = new JLabel("Rock Level");
		
		JSlider slider_3 = new JSlider();
		slider_3.setValue(100);
		slider_3.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				lblRockLevel.setText("Rock Level: " + slider_3.getValue());
				slider_2.setMaximum(slider_3.getValue());
				gen.setWRC((double)(slider_3.getValue())/100);
				update();
				
			}
		});
		panel = new PicturePanel(null);
		
		JLabel lblRouthness = new JLabel("Routhness: 60");
		
		JSlider slider_4 = new JSlider();
		slider_4.setValue(60);
		slider_4.setMinimum(1);
		slider_4.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				lblRouthness.setText("Routhness: " + slider_4.getValue());
				
			}
		});
		
		JLabel lblFallof = new JLabel("Fallof: 90");
		
		JSlider slider_5 = new JSlider();
		slider_5.setValue(90);
		slider_5.setMinimum(1);
		slider_5.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				lblFallof.setText("Fallof: " + slider_5.getValue());
				
			}
		});
		
		JLabel lblSmoothing = new JLabel("Smoothing: 25");
		
		JSlider slider_6 = new JSlider();
		slider_6.setValue(25);
		slider_6.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				lblSmoothing.setText("Smoothing: " + slider_6.getValue());
				
			}
		});
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int WW = slider.getValue();
				int WS = slider_1.getValue();
				int WG = slider_2.getValue();
				int WR = slider_3.getValue();
				int routh = slider_4.getValue();
				int fallof = slider_5.getValue();
				int smooth = slider_6.getValue();
				
				gen = new WorldGen((double)(WW)/100, (double)(WS)/100, (double)(WG)/100, (double)(WR)/100, (float)(routh)/100, (float)(fallof)/100, smooth);
				
				gen.gen(Integer.parseInt(et_seed.getText()));
				update();
				
			}
		});
		
		JButton btnRandome_1 = new JButton(button_rand);//wasser
		btnRandome_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double rand = Math.random()*25;
				slider.setValue((int)rand);
				lblWaterLevel.setText("Water Level: " + slider.getValue());
				gen.setWWC((double)(slider.getValue())/100);
				update();
			}
		});
		
		JButton btnRandome_2 = new JButton(button_rand);//sand
		btnRandome_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double rand = Math.random()*25;
				slider_1.setValue(25+(int)rand);
				lblSandLevel.setText("Sand Level: " + slider_1.getValue());
				gen.setWSC((double)(slider_1.getValue())/100);
				update();
				
			}
		});
		
		JButton btnRandome_3 = new JButton(button_rand);//Gras
		btnRandome_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double rand = Math.random()*30;
				slider_2.setValue(60+(int)rand);
				lblGrasLevel.setText("Gras Level: " + slider_2.getValue());
				gen.setWGC((double)(slider_2.getValue())/100);
				update();
				
			}
		});
		
		
		
		
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 520, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblWaterLevel)
										.addComponent(et_seed, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
									.addGap(26)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnRandome_1)
										.addComponent(btnRandome)))
								.addComponent(slider_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
								.addComponent(slider, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(lblSandLevel)
									.addPreferredGap(ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
									.addComponent(btnRandome_2))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(lblGrasLevel)
									.addPreferredGap(ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
									.addComponent(btnRandome_3))
								.addComponent(slider_3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
								.addComponent(slider_2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
								.addComponent(slider_5, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
								.addComponent(slider_4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
								.addComponent(slider_6, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
								.addComponent(btnGenerate, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
								.addComponent(lblFallof)
								.addComponent(lblRouthness)
								.addComponent(lblRockLevel)
								.addComponent(lblSmoothing)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addComponent(lblSeed)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblRouthness)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblFallof)
							.addGap(1)
							.addComponent(slider_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblSeed)
							.addGap(9)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(et_seed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnRandome))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWaterLevel)
								.addComponent(btnRandome_1))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnRandome_2)
								.addComponent(lblSandLevel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnRandome_3)
								.addComponent(lblGrasLevel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblRockLevel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblSmoothing)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
							.addComponent(btnGenerate))
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void update(){
		File input = new File("C:/output/CM.png");
		try {
			panel.setPic(ImageIO.read(input));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
