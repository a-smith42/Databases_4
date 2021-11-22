import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

@SuppressWarnings("serial")
public class JDBCMainWindowContent extends JInternalFrame implements ActionListener {
	String cmd = null;

	// DB Connectivity Attributes
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private Container content;

	private JPanel detailsPanel;
	private JPanel exportButtonPanel;
	// private JPanel exportConceptDataPanel;
	private JScrollPane dbContentsPanel;

	private Border lineBorder;

	private JLabel IDLabel = new JLabel("ID:                 ");
	private JLabel ArtistNameLabel = new JLabel("ArtistName:               ");
	private JLabel TrackNameLabel = new JLabel("TrackName:      ");
	private JLabel AlbumNameLabel = new JLabel("AlbumName:      ");
	private JLabel MilsecPlayedLabel = new JLabel("MilsecPlayed:        ");
	private JLabel DanceabilityLabel = new JLabel("Danceability:                 ");
	private JLabel EnergyLabel = new JLabel("Energy:               ");
	private JLabel LoudnessLabel = new JLabel("Loudness:      ");
	private JLabel SpeechinessLabel = new JLabel("Speechiness:      ");
	private JLabel AcousticnessLabel = new JLabel("Acousticness:        ");
	private JLabel InstrumentalnessLabel = new JLabel("Instrumentalness:        ");
	private JLabel LivenessLabel = new JLabel("Liveness:        ");
	private JLabel ValenceLabel = new JLabel("Valence:        ");
	private JLabel TempoLabel = new JLabel("Tempo:        ");
	private JLabel MilsecDurationLabel = new JLabel("MilsecDuration:        ");

	private JTextField IDTF = new JTextField(10);
	private JTextField ArtistNameTF = new JTextField(10);
	private JTextField TrackNameTF = new JTextField(10);
	private JTextField AlbumNameTF = new JTextField(10);
	private JTextField MilsecPlayedTF = new JTextField(10);
	private JTextField DanceabilityTF = new JTextField(10);
	private JTextField EnergyTF = new JTextField(10);
	private JTextField LoudnessTF = new JTextField(10);
	private JTextField SpeechinessTF = new JTextField(10);
	private JTextField AcousticnessTF = new JTextField(10);
	private JTextField InstrumentalnessTF = new JTextField(10);
	private JTextField LivenessTF = new JTextField(10);
	private JTextField ValenceTF = new JTextField(10);
	private JTextField TempoTF = new JTextField(10);
	private JTextField MilsecDurationTF = new JTextField(10);

	private static QueryTableModel TableModel = new QueryTableModel();
	// Add the models to JTabels
	private JTable TableofDBContents = new JTable(TableModel);
	// Buttons for inserting, and updating members
	// also a clear button to clear details panel
	private JButton updateButton = new JButton("Update");
	private JButton insertButton = new JButton("Insert");
	private JButton exportButton = new JButton("Export");
	private JButton deleteButton = new JButton("Delete");
	private JButton clearButton = new JButton("Clear");

	private JButton chartButton = new JButton("Bop Chart");
	
	private JButton NumTracks = new JButton("Num Tracks Per Artist:");
	private JTextField NumTracksTF = new JTextField(12);
	private JButton avgTrackDuration = new JButton("AVG Track Duration");
	private JButton mostplayedButton = new JButton("Most Played Track");
	private JButton artistsButton = new JButton("List All Artists");
	private JButton albumsButton = new JButton("List All Albums");

	public JDBCMainWindowContent(String aTitle) 
	{
		
		// setting up the GUI
		super(aTitle, false, false, false, false);
		setEnabled(true);

		Color c1 = new Color(203, 192, 209);
		Color c2 = new Color(240, 192, 0);
		Color c3 = new Color(157, 145, 163);
		
		initiate_db_conn();
		// add the 'main' panel to the Internal Frame
		content = getContentPane();
		content.setLayout(null);
		content.setBackground(c1);
		lineBorder = BorderFactory.createEtchedBorder(15, c2, Color.black);

		// setup details panel and add the components to it
		detailsPanel = new JPanel();
		detailsPanel.setLayout(new GridLayout(15, 3));
		detailsPanel.setBackground(c3);
		detailsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "CRUD Actions"));

		detailsPanel.add(IDLabel);
		detailsPanel.add(IDTF);
		detailsPanel.add(ArtistNameLabel);
		detailsPanel.add(ArtistNameTF);
		detailsPanel.add(TrackNameLabel);
		detailsPanel.add(TrackNameTF);
		detailsPanel.add(AlbumNameLabel);
		detailsPanel.add(AlbumNameTF);
		detailsPanel.add(MilsecPlayedLabel);
		detailsPanel.add(MilsecPlayedTF);
		detailsPanel.add(DanceabilityLabel);
		detailsPanel.add(DanceabilityTF);
		detailsPanel.add(EnergyLabel);
		detailsPanel.add(EnergyTF);
		detailsPanel.add(LoudnessLabel);
		detailsPanel.add(LoudnessTF);
		detailsPanel.add(SpeechinessLabel);
		detailsPanel.add(SpeechinessTF);
		detailsPanel.add(AcousticnessLabel);
		detailsPanel.add(AcousticnessTF);
		detailsPanel.add(InstrumentalnessLabel);
		detailsPanel.add(InstrumentalnessTF);
		detailsPanel.add(LivenessLabel);
		detailsPanel.add(LivenessTF);
		detailsPanel.add(ValenceLabel);
		detailsPanel.add(ValenceTF);
		detailsPanel.add(TempoLabel);
		detailsPanel.add(TempoTF);
		detailsPanel.add(MilsecDurationLabel);
		detailsPanel.add(MilsecDurationTF);

		// setup details panel and add the components to it
		exportButtonPanel = new JPanel();
		exportButtonPanel.setLayout(new GridLayout(3, 2));
		exportButtonPanel.setBackground(c3);
		exportButtonPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Export Data"));
		exportButtonPanel.add(NumTracks);
		exportButtonPanel.add(NumTracksTF);
		exportButtonPanel.add(avgTrackDuration);
		exportButtonPanel.add(mostplayedButton);
		exportButtonPanel.add(artistsButton);
		exportButtonPanel.add(albumsButton);
		exportButtonPanel.setSize(400, 200);
		exportButtonPanel.setLocation(1150, 425);
		content.add(exportButtonPanel);

		insertButton.setSize(100, 30);
		updateButton.setSize(100, 30);
		exportButton.setSize(100, 30);
		deleteButton.setSize(100, 30);
		clearButton.setSize(100, 30);
		chartButton.setSize(100, 30);

		insertButton.setLocation(430, 40);
		updateButton.setLocation(430, 117);
		exportButton.setLocation(430, 195);
		deleteButton.setLocation(430, 272);
		clearButton.setLocation(430, 350);
		chartButton.setLocation(430, 420);

		insertButton.addActionListener(this);
		updateButton.addActionListener(this);
		exportButton.addActionListener(this);
		deleteButton.addActionListener(this);
		clearButton.addActionListener(this);

		chartButton.addActionListener(this);

		this.albumsButton.addActionListener(this);
		this.NumTracks.addActionListener(this);
		this.artistsButton.addActionListener(this);
		this.avgTrackDuration.addActionListener(this);
		this.mostplayedButton.addActionListener(this);

		content.add(insertButton);
		content.add(updateButton);
		content.add(exportButton);
		content.add(deleteButton);
		content.add(clearButton);

		content.add(chartButton);

		TableofDBContents.setPreferredScrollableViewportSize(new Dimension(900, 300));

		dbContentsPanel = new JScrollPane(TableofDBContents, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel.setBackground(c3);
		dbContentsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Database Content"));

		detailsPanel.setSize(400, 400);
		detailsPanel.setLocation(10, 10);
		dbContentsPanel.setSize(1000, 400);
		dbContentsPanel.setLocation(550, 10);

		content.add(detailsPanel);
		content.add(dbContentsPanel);

		setSize(982, 645);
		setVisible(true);

		TableModel.refreshFromDB(stmt);
	}

	public void initiate_db_conn() 
	{
		try
		{
			// Load the JConnector Driver
			Class.forName("com.mysql.jdbc.Driver");
			// Specify the DB Name
			String url = "jdbc:mysql://localhost:3306/JDBC_Assignment?serverTimezone=UTC";
			// Connect to DB using DB URL, Username and password
			/*Home*/ //con = DriverManager.getConnection(url, "root", "admin");
			/*College*/con = DriverManager.getConnection(url, "root", "root");
			// Create a generic statement which is passed to the TestInternalFrame1
			stmt = con.createStatement();
		} 
		catch (Exception e) 
		{
			System.out.println("Error: Failed to connect to database\n" + e.getMessage());
		}
	}

	//******PIE CHART*******
	public  void pieGraph(ResultSet rs, String title) 
	{
		try 
		{
			DefaultPieDataset dataset = new DefaultPieDataset();

			while (rs.next())
			{
				String category = rs.getString(1);
				String value = rs.getString(2);
				dataset.setValue(category+ " "+value, new Double(value));
			}
			JFreeChart chart = ChartFactory.createPieChart(
					title,  
					dataset,             
					false,              
					true,
					true
			);

			ChartFrame frame = new ChartFrame(title, chart);
			chart.setBackgroundPaint(Color.WHITE);
			frame.pack();
			frame.setSize(1000, 750);
			frame.setVisible(true);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// event handling
	public void actionPerformed(ActionEvent e) 
	{
		Object target = e.getSource();
		//*********CLEAR*********
		if (target == clearButton) 
		{
			IDTF.setText("");
			ArtistNameTF.setText("");
			TrackNameTF.setText("");
			AlbumNameTF.setText("");
			MilsecPlayedTF.setText("");
			DanceabilityTF.setText("");
			EnergyTF.setText("");
			LoudnessTF.setText("");
			SpeechinessTF.setText("");
			AcousticnessTF.setText("");
			InstrumentalnessTF.setText("");
			LivenessTF.setText("");
			ValenceTF.setText("");
			TempoTF.setText("");
			MilsecDurationTF.setText("");
		}

		//*********INSERT**********
		if (target == insertButton)
		{
			try 
			{
				String updateTemp = "INSERT INTO tracks VALUES(" + IDTF.getText() + ",'" + ArtistNameTF.getText()
						+ "','" + TrackNameTF.getText() + "'," + AlbumNameTF.getText() + ",'" + MilsecPlayedTF.getText()
						+ "','" + DanceabilityTF.getText() + "','" + EnergyTF.getText() + "'," + LoudnessTF.getText()
						+ "," + SpeechinessTF.getText() + "," + AcousticnessTF.getText() + ","
						+ InstrumentalnessTF.getText() + "," + LivenessTF.getText() + "," + ValenceTF.getText() + ","
						+ TempoTF.getText() + "," + MilsecDurationTF.getText() + ");";

				System.out.println(updateTemp);
				stmt.executeUpdate(updateTemp);

			} 
			catch (SQLException sqle)
			{
				System.err.println("Error with  insert:\n" + sqle.toString());
			} 
			finally 
			{
				TableModel.refreshFromDB(stmt);
			}
		}
		
		//**********DELETE**********
		if (target == deleteButton) 
		{
			try 
			{
				String updateTemp = "DELETE FROM tracks WHERE id = " + IDTF.getText() + ";";
				stmt.executeUpdate(updateTemp);

			} 
			catch (SQLException sqle)
			{
				System.err.println("Error with delete:\n" + sqle.toString());
			} 
			finally 
			{
				TableModel.refreshFromDB(stmt);
			}
		}
		
		//***********UPDATE**********
		if (target == updateButton) 
		{
			try 
			{
				String updateTemp = "UPDATE tracks SET " + "artistName = '" + ArtistNameTF.getText()
						+ "', trackName = '" + TrackNameTF.getText() + "', albumName = " + AlbumNameTF.getText()
						+ ", milsecPlayed ='" + MilsecPlayedTF.getText() + "', danceability = '"
						+ DanceabilityTF.getText() + "', energy = '" + EnergyTF.getText() + "', loudness = "
						+ LoudnessTF.getText() + ", speechiness = " + SpeechinessTF.getText() + ", acousticness = "
						+ AcousticnessTF.getText() + ", instrumentalness = " + InstrumentalnessTF.getText()
						+ ", liveness = " + LivenessTF.getText() + ", valence = " + ValenceTF.getText() + ", tempo = "
						+ TempoTF.getText() + ", milsecDuration = " + MilsecDurationTF.getText() + " where id = "
						+ IDTF.getText();

				stmt.executeUpdate(updateTemp);
				// these lines do nothing but the table updates when we access the db.
				rs = stmt.executeQuery("SELECT * from tracks ");
				rs.next();
				rs.close();
			} 
			catch (SQLException sqle) 
			{
				System.err.println("Error with  update:\n" + sqle.toString());
			} 
			finally 
			{
				TableModel.refreshFromDB(stmt);
			}
		}
		
		if (target.equals(exportButton))
		{  		
			//set cmd to write out all the table data to the csv
			cmd = "select * from tracks;";
			
			try 
			{
				rs= stmt.executeQuery(cmd); 
				writeToFile(rs);
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}
		
		if (target.equals(chartButton))
		{  		
				cmd = "select concat(trackName, \" by \",  artistName, \":\"), round(energy+danceability+loudness+tempo+valence-acousticness-speechiness-instrumentalness+liveness) as \"Bopiness\" from tracks order by Bopiness asc;";
				try 
				{
					rs= stmt.executeQuery(cmd);
				}
				catch (SQLException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				pieGraph(rs, "\"How much of a bop is it?\" Statistics");				
		}
			


		//******LIST ALL ARTISTS********
		if (target == this.artistsButton) 
		{
			System.out.println("artists pressed");

			cmd = "select distinct artistName from tracks order by artistName asc;";

			System.out.println(cmd);
			try 
			{
				rs = stmt.executeQuery(cmd);
				writeToFile(rs);
				System.out.println(rs.toString());
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}

		//******LIST ALL ALBUMS********
		if (target == this.albumsButton) 
		{
			System.out.println("albums pressed");

			cmd = "select distinct albumName from tracks order by albumName asc;";

			System.out.println(cmd);
			try 
			{
				rs = stmt.executeQuery(cmd);
				writeToFile(rs);
				System.out.println(rs.toString());
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}
		
		//******LIST NUM TRACKS PER CHOSEN ARTIST********
		if (target == this.NumTracks) 
		{
			System.out.println("num tracks pressed");
			String artistNameQ = this.NumTracksTF.getText();

			cmd = "select artistName, count(*) " + "from tracks " + "where artistName = '" + artistNameQ + "';";

			System.out.println(cmd);
			try 
			{
				rs = stmt.executeQuery(cmd);
				writeToFile(rs);
				System.out.println(rs.toString());
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}
		
		//******AVG TRACK DURATION********
		if (target == this.avgTrackDuration) 
		{
			System.out.println("track duration pressed");

			cmd = "SELECT AVG(milsecduration) as AverageTrackDuration FROM tracks;";

			System.out.println(cmd);
			try 
			{
				rs = stmt.executeQuery(cmd);
				writeToFile(rs);
				System.out.println(rs.toString());
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}
		
		//******MOST PLAYED TRACK********
		if (target == this.mostplayedButton) 
		{
			System.out.println("most played pressed");
			
			cmd = "SELECT trackname, artistname, max(milsecplayed) from tracks;";
			
			System.out.println(cmd);
			try 
			{
				rs = stmt.executeQuery(cmd);
				writeToFile(rs);
				System.out.println(rs.toString());
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}
	}

	private void writeToFile(ResultSet rs) 
	{
		try 
		{
			System.out.println("In writeToFile");
			FileWriter outputFile = new FileWriter("track_details.csv");
			PrintWriter printWriter = new PrintWriter(outputFile);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();

			for (int i = 0; i < numColumns; i++) 
			{
				printWriter.print(rsmd.getColumnLabel(i + 1) + ",");
			}
			printWriter.print("\n");
			
			while (rs.next()) 
			{
				for (int i = 0; i < numColumns; i++) 
				{
					printWriter.print(rs.getString(i + 1) + ",");
				}
				printWriter.print("\n");
				printWriter.flush();
			}
			printWriter.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
