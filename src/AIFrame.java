// ChatGPTFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AIFrame extends JFrame {

    private final JTextField promptField;
    private JButton generateButton;
    private JTextArea responseArea;
    private OpenAIClient apiClient;

    public AIFrame() {
        // Initialize the JFrame
        setTitle("Food & Nutrition Question Generator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize API client
        apiClient = new OpenAIClient();

        // Create UI components
        promptField = new JTextField(30);
        generateButton = new JButton("Generate Question");
        responseArea = new JTextArea(10, 40);
        responseArea.setLineWrap(true);
        responseArea.setWrapStyleWord(true);
        responseArea.setEditable(false);

        // Add components to the frame
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter your prompt:"));
        inputPanel.add(promptField);
        inputPanel.add(generateButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(responseArea), BorderLayout.CENTER);

        // Button action listener
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prompt = promptField.getText();
                if (!prompt.isEmpty()) {
                    // Make an API call to OpenAI
                    responseArea.setText("Generating questions...");
                    String response = apiClient.generateQuestions(prompt);
                    responseArea.setText(response);
                } else {
                    responseArea.setText("Please enter a valid prompt.");
                }
            }
        });
    }

    public static void main(String[] args) {
        // Run the JFrame application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AIFrame().setVisible(true);
            }
        });
    }
}
