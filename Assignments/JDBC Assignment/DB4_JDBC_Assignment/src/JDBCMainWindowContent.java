import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;

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

	private JButton NumTracks = new JButton("NumTracksPerArtist:");
	private JTextField NumTracksTF = new JTextField(12);
	private JButton avgTrackDuration = new JButton("avgTrackDuration");
	private JTextField avgTrackDurationTF = new JTextField(12);
	private JButton ListAllArtists = new JButton("ListAllArtists");
	private JButton ListAllAlbums = new JButton("ListAllAlbums");

	public JDBCMainWindowContent(String aTitle) 
	{
		// setting up the GUI
		super(aTitle, false, false, false, false);
		setEnabled(true);

		initiate_db_conn();
		// add the 'main' panel to the Internal Frame
		content = getContentPane();
		content.setLayout(null);
		content.setBackground(Color.lightGray);
		lineBorder = BorderFactory.createEtchedBorder(15, Color.red, Color.black);

		// setup details panel and add the components to it
		detailsPanel = new JPanel();
		detailsPanel.setLayout(new GridLayout(11, 2));
		detailsPanel.setBackground(Color.lightGray);
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
		exportButtonPanel.setBackground(Color.lightGray);
		exportButtonPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Export Data"));
		exportButtonPanel.add(NumTracks);
		exportButtonPanel.add(NumTracksTF);
		exportButtonPanel.add(avgTrackDuration);
		exportButtonPanel.add(avgTrackDurationTF);
		exportButtonPanel.add(ListAllArtists);
		exportButtonPanel.add(ListAllAlbums);
		exportButtonPanel.setSize(500, 200);
		exportButtonPanel.setLocation(3, 300);
		content.add(exportButtonPanel);

		insertButton.setSize(100, 30);
		updateButton.setSize(100, 30);
		exportButton.setSize(100, 30);
		deleteButton.setSize(100, 30);
		clearButton.setSize(100, 30);

		insertButton.setLocation(370, 10);
		updateButton.setLocation(370, 110);
		exportButton.setLocation(370, 160);
		deleteButton.setLocation(370, 60);
		clearButton.setLocation(370, 210);

		insertButton.addActionListener(this);
		updateButton.addActionListener(this);
		exportButton.addActionListener(this);
		deleteButton.addActionListener(this);
		clearButton.addActionListener(this);

		this.ListAllAlbums.addActionListener(this);
		this.NumTracks.addActionListener(this);

		content.add(insertButton);
		content.add(updateButton);
		content.add(exportButton);
		content.add(deleteButton);
		content.add(clearButton);

		TableofDBContents.setPreferredScrollableViewportSize(new Dimension(900, 300));

		dbContentsPanel = new JScrollPane(TableofDBContents, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel.setBackground(Color.lightGray);
		dbContentsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Database Content"));

		detailsPanel.setSize(360, 300);
		detailsPanel.setLocation(3, 0);
		dbContentsPanel.setSize(700, 300);
		dbContentsPanel.setLocation(477, 0);

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
			con = DriverManager.getConnection(url, "root", "admin");
			// Create a generic statement which is passed to the TestInternalFrame1
			stmt = con.createStatement();
		} 
		catch (Exception e) 
		{
			System.out.println("Error: Failed to connect to database\n" + e.getMessage());
		}
	}

	// event handling
	public void actionPerformed(ActionEvent e) 
	{
		Object target = e.getSource();
		if (target == clearButton) {
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


		if (target == this.ListAllArtists) 
		{

			cmd = "select distinct artistName from tracks;";

			try 
			{
				rs = stmt.executeQuery(cmd);
				writeToFile(rs);
				System.out.println(rs);
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}

		if (target == this.NumTracks) 
		{
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
