import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import javax.swing.GroupLayout.Alignment;

@SuppressWarnings("serial")
public class OpenFileGUI extends  JFrame {

	private final int width =  300;
	private final int height = 200;

	private String  frameTitle = "File Checker";

	/**
	 * Frame components
	 */
	private JButton openFileButton;

	private JTextArea fileType;

	private final JFileChooser openFileChooser;

	public OpenFileGUI() { 

		openFileButton = new JButton("Open file...");

		fileType = new JTextArea("File description...");

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()

				.addGroup(layout.createParallelGroup(Alignment.LEADING, true)
						.addComponent(openFileButton)
						.addComponent(fileType))
				);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(openFileButton)
				.addComponent(fileType)
				);

		pack();
		setSize(width, height);
		setMinimumSize(new Dimension(width, height));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		/**
		 * File Chooser
		 */
		openFileChooser = new JFileChooser();
		openFileChooser.setCurrentDirectory(new File("C:/Users/Stefania/Documents"));
		openFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		/**
		 * Action listeners
		 */
		openFileButton.addActionListener((ActionEvent event) -> {

			int returnValue = openFileChooser.showOpenDialog(this);

			if (returnValue == JFileChooser.APPROVE_OPTION) {

				File file = openFileChooser.getSelectedFile();
				fileType.setText("File name: " + file.getName() + "\n");

				try {
					fileType.append(properties(file));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

				fileType.setText("File cannot be opened.");

			}

		});

	}

	private String properties(File file) throws IOException {

		String result = "";
		//directory
		if (file.isDirectory())  {

			int countFirstLevel;
			
			/*
			String[] files = file.list();
			
			for(int i = 0; i < files.length; i++)
				System.out.println(files[i]);
			*/
			
			result = "File  type:  directory.\nSize: " + folderSize(file)/1000 + "kB\nNumber of files  on first level: " + file.list().length;

		} else {
			
			if (file.isFile()) {

				//*.txt file
				if (file.toString().toLowerCase().endsWith(".txt") == true) {

					result = "File type: *.txt file.\n";

					String line1 = "", line2 = "", line3 = "", line4 = "", line5 = "";

					FileInputStream in = new FileInputStream(file.toString());
					BufferedReader br = new BufferedReader(new InputStreamReader(in));

					String tmp;
					while ((tmp = br.readLine()) != null) {
						line1 = line2;
						line2 = line3;
						line3 = line4;
						line4 = line5;
						line5 = tmp;
					}
					in.close();  

					result += line1 + "\n" + line2 + "\n" + line3 + "\n" + line4  + "\n" + line5;

					/* 
					OR:					
					String tmp;
					String[] line  = new  String[4];
					for (int i = 0; i < 4; i++) line[i] = "";
					while ((tmp = br.readLine()) != null) {

						for (int i = 0; i < 3; i++)
							line[i] = line[i+1];
						line[4] = tmp;

					}
					in.close();  

					for (int i = 0; i  < 4; i++)
						result += line[i] + "\n";
					 */
				}

				//*.zip file
				if (file.toString().toLowerCase().endsWith(".zip") == true) {
					
					result = "File type: *.zip file.\nUnzipping the file we get: \n";
					
					ZipInputStream zin = new ZipInputStream(new FileInputStream(file.toString()));
					ZipEntry zen = null;
					
					int i = 1;
				    while ((zen  = zin.getNextEntry()) != null) {
				    	
				    	result += i + "). " + zen.getName().toString() + "\n";
				    	i++;
				    }
					
				}
				
				//*.png *.jpeg *.gif *.bmp
				if ((file.toString().toLowerCase().endsWith(".png") ||
						file.toString().toLowerCase().endsWith(".jpeg") ||
							file.toString().toLowerCase().endsWith(".gif") ||
								file.toString().toLowerCase().endsWith(".bmp")) == true) {
					
					result = "File type: image.\n";
					
					try(ImageInputStream in = ImageIO.createImageInputStream(file)){
					    final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
					    if (readers.hasNext()) {
					        ImageReader reader = readers.next();
					        try {
					            reader.setInput(in);
					            result += "Width: " + reader.getWidth(0) + " Heigth: " + reader.getHeight(0);
					        } finally {
					            reader.dispose();
					        }
					    }
					}//end try
					
				}
				
			} else {
				
				result = "Not our file type.";
				
			}

		}//end if directory

		return result;
	}

	private long folderSize(File  directory) {

		long length = 0;

		for (File file : directory.listFiles()) {

			if(file.isFile())
				length +=  file.length();
			else
				length  +=  folderSize(file);	

		}

		return length;
	}

	public void start() {

		setTitle(frameTitle);
		setVisible(true);

	}

	public static void main(String[] args) {

		OpenFileGUI instance = new OpenFileGUI();
		instance.start();

	}
}
