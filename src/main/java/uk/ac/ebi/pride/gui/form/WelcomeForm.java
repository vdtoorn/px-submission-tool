package uk.ac.ebi.pride.gui.form;

import uk.ac.ebi.pride.archive.dataprovider.project.SubmissionType;
import uk.ac.ebi.pride.gui.GUIUtilities;
import uk.ac.ebi.pride.gui.form.action.LoadSubmissionFileAction;
import uk.ac.ebi.pride.gui.form.comp.HeaderPanel;
import uk.ac.ebi.pride.gui.form.dialog.ResubmissionDialog;
import uk.ac.ebi.pride.gui.util.BorderUtil;
import uk.ac.ebi.pride.gui.util.ColourUtil;
import uk.ac.ebi.pride.gui.util.HttpUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This form shows welcome messages
 *
 * @author Rui Wang
 * @version $Id$
 */
public class WelcomeForm extends Form {

    private static final String FULL_SUBMISSION_OPTION = "FULL_SUBMISSION";
    private static final String PARTIAL_SUBMISSION_OPTION = "PARTIAL_SUBMISSION";


    private ResubmissionDialog resubmissionDialog;


    public WelcomeForm() {
        initComponents();
    }

    private void initComponents() {
        // setup the main pane
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        // create mission statement panel
        JPanel submissionOptionPanel = createSubmissionOptionPanel();
        this.add(submissionOptionPanel, BorderLayout.CENTER);
    }

    /**
     * Panel allows users to choose which type of submission they would like to perform
     */
    private JPanel createSubmissionOptionPanel() {
        JPanel optionPanel = new JPanel(new BorderLayout());
        optionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
//        optionPanel.setBorder(BorderUtil.createLoweredBorder());

        // title panel
        optionPanel.add(createOptionTitlePanel(), BorderLayout.NORTH);

        // button panel for choosing the submission type
        optionPanel.add(createOptionButtonPanel(), BorderLayout.CENTER);

        // external link panel
        optionPanel.add(createOptionLinkPanel(), BorderLayout.SOUTH);

        return optionPanel;
    }

    /**
     * Create a title panel for the option panel
     */
    private JPanel createOptionTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel(appContext.getProperty("welcome.before.start.title"));
        titleLabel.setFont(titlePanel.getFont().deriveFont(16f).deriveFont(Font.BOLD));
        titlePanel.add(titleLabel, BorderLayout.NORTH);
//        JLabel descPanel = new JLabel(appContext.getProperty("welcome.before.start.desc"));
//        titlePanel.add(descPanel, BorderLayout.CENTER);
        titlePanel.add(Box.createRigidArea(new Dimension(10, 10)), BorderLayout.SOUTH);

        return titlePanel;
    }

    /**
     * Create a button panel for the option panel
     */
    private JPanel createOptionButtonPanel() {
        JPanel submissionOptionPanel = new JPanel();
        submissionOptionPanel.setBorder(BorderUtil.createLoweredBorder());
        GridLayout gridLayout = new GridLayout(2, 1);
        gridLayout.setVgap(20);
        submissionOptionPanel.setLayout(gridLayout);

        ButtonGroup buttonGroup = new ButtonGroup();
        // action listener
        ActionListener submissionOptionListener = new SubmissionOptionListener();


        // complete submission panel
        JPanel completeSubmissionPanel = new JPanel(new BorderLayout());
        HeaderPanel completeSubmissionHeaderPanel = new HeaderPanel();
        completeSubmissionHeaderPanel.setLayout(new BoxLayout(completeSubmissionHeaderPanel, BoxLayout.X_AXIS));
        completeSubmissionHeaderPanel.setPreferredSize(new Dimension(60, 20));

        completeSubmissionHeaderPanel.add(Box.createHorizontalGlue());
        JCheckBox completeSubmissionCheckBox = new JCheckBox();
        buttonGroup.add(completeSubmissionCheckBox);
        completeSubmissionCheckBox.setActionCommand(FULL_SUBMISSION_OPTION);
        completeSubmissionCheckBox.addActionListener(submissionOptionListener);
        completeSubmissionHeaderPanel.add(completeSubmissionCheckBox);
        completeSubmissionHeaderPanel.add(Box.createHorizontalGlue());
        completeSubmissionPanel.add(completeSubmissionHeaderPanel, BorderLayout.WEST);
        completeSubmissionCheckBox.doClick();

        String completeSubmissionTitle = appContext.getProperty("welcome.full.submission.title");
        String completeSubmissionContent = appContext.getProperty("welcome.full.submission.content");
        JPanel completeSubmissionContentPanel = createSubmissionOptionPanel(completeSubmissionTitle, completeSubmissionContent, true);
        completeSubmissionPanel.add(completeSubmissionContentPanel);
        submissionOptionPanel.add(completeSubmissionPanel);

        // partial submission panel
        JPanel partialSubmissionPanel = new JPanel(new BorderLayout());
        HeaderPanel partialSubmissionHeaderPanel = new HeaderPanel(new BorderLayout());
        partialSubmissionHeaderPanel.setLayout(new BoxLayout(partialSubmissionHeaderPanel, BoxLayout.X_AXIS));
        partialSubmissionHeaderPanel.setPreferredSize(new Dimension(60, 20));

        partialSubmissionHeaderPanel.add(Box.createHorizontalGlue());
        JCheckBox partialSubmissionCheckBox = new JCheckBox();
        buttonGroup.add(partialSubmissionCheckBox);
        partialSubmissionCheckBox.setActionCommand(PARTIAL_SUBMISSION_OPTION);
        partialSubmissionCheckBox.addActionListener(submissionOptionListener);
        partialSubmissionHeaderPanel.add(partialSubmissionCheckBox);
        partialSubmissionHeaderPanel.add(Box.createHorizontalGlue());
        partialSubmissionPanel.add(partialSubmissionHeaderPanel, BorderLayout.WEST);

        String partialSubmissionTitle = appContext.getProperty("welcome.partial.submission.title");
        String partialSubmissionContent = appContext.getProperty("welcome.partial.submission.content");
        JPanel partialSubmissionContentPanel = createSubmissionOptionPanel(partialSubmissionTitle, partialSubmissionContent, false);
        partialSubmissionPanel.add(partialSubmissionContentPanel);

        submissionOptionPanel.add(partialSubmissionPanel);

        return submissionOptionPanel;
    }

    public JPanel createSubmissionOptionPanel(String title, String content, boolean recommend) {
        JPanel contentPanel = new HeaderPanel();
        contentPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel();
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(titleLabel.getFont().deriveFont(20f).deriveFont(Font.BOLD));
        titleLabel.setText(title);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(5, 5)));
        if (recommend) {
            JLabel refLabel = new JLabel(" (recommended)");
            refLabel.setForeground(Color.green);
            refLabel.setFont(refLabel.getFont().deriveFont(18f).deriveFont(Font.BOLD));
            titlePanel.add(refLabel);
        }


        JLabel messageLabel = new JLabel();
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
        messageLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        messageLabel.setVerticalTextPosition(SwingConstants.TOP);
        messageLabel.setForeground(Color.white);
        messageLabel.setFont(messageLabel.getFont().deriveFont(16f));
        messageLabel.setText(content);

        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(messageLabel, BorderLayout.CENTER);

        return contentPanel;
    }

    /**
     * Panel contains the external links
     */
    private JPanel createOptionLinkPanel() {
        JPanel linkPanel = new JPanel();
        linkPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // resubmission dialog
        resubmissionDialog = new ResubmissionDialog(app.getMainFrame());
        resubmissionDialog.setLocationRelativeTo(app.getMainFrame());
        resubmissionDialog.setVisible(false);

        Icon icon = GUIUtilities.loadIcon(appContext.getProperty("resubmission.button.small.icon"));
        JButton resubmissionButton = GUIUtilities.createLabelLikeButton(icon, appContext.getProperty("resubmission.button.label"));
        resubmissionButton.setForeground(ColourUtil.HYPERLINK_COLOUR);
        resubmissionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resubmissionDialog.updateState();
                resubmissionDialog.setVisible(true);
            }
        });

        linkPanel.add(resubmissionButton);

        // bulk submission
        JButton loadSubmissionFileButton = GUIUtilities.createLabelLikeButton(new LoadSubmissionFileAction());
        loadSubmissionFileButton.setForeground(ColourUtil.HYPERLINK_COLOUR);
        linkPanel.add(loadSubmissionFileButton);

        // submission guideline
        icon = GUIUtilities.loadIcon(appContext.getProperty("welcome.submission.tool.link.small.icon"));
        JButton dataButton = GUIUtilities.createLabelLikeButton(icon, appContext.getProperty("welcome.submission.tool.submission.guideline"));
        dataButton.setForeground(ColourUtil.HYPERLINK_COLOUR);
        dataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                HttpUtil.openURL(appContext.getProperty("px.submission.guideline.web.url"));
            }
        });
        linkPanel.add(dataButton);

        // more about proteomexchagne
        JButton moreButton = GUIUtilities.createLabelLikeButton(icon, appContext.getProperty("welcome.submission.tool.more.detail"));
        moreButton.setForeground(ColourUtil.HYPERLINK_COLOUR);
        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                HttpUtil.openURL(appContext.getProperty("px.web.url"));
            }
        });
        linkPanel.add(moreButton);

        return linkPanel;
    }


    /**
     * Listener to perform actions when a submission option is selected
     */
    private class SubmissionOptionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(FULL_SUBMISSION_OPTION)) {
                // set submission option in application context
                appContext.setSubmissionsType(SubmissionType.COMPLETE);
                // change the colour of the option button
//                fullSubmissionButton.setForeground(Color.BLACK);
//                partialSubmissionButton.setForeground(Color.GRAY);
//                fullSubmissionButton.setIcon(GUIUtilities.loadIcon(appContext.getProperty("welcome.full.submission.button.title.selected.large.icon")));
//                partialSubmissionButton.setIcon(GUIUtilities.loadIcon(appContext.getProperty("welcome.partial.submission.button.title.large.icon")));
//                minimumSubmissionButton.setIcon(GUIUtilities.loadIcon(appContext.getProperty("welcome.minimum.submission.button.title.large.icon")));
                // change the required information
            } else if (e.getActionCommand().equals(PARTIAL_SUBMISSION_OPTION)) {
                // set submission option in application context
                appContext.setSubmissionsType(SubmissionType.PARTIAL);

                // change the colour of the option button
//                fullSubmissionButton.setForeground(Color.GRAY);
//                partialSubmissionButton.setForeground(Color.BLACK);
//                fullSubmissionButton.setIcon(GUIUtilities.loadIcon(appContext.getProperty("welcome.full.submission.button.title.large.icon")));
//                partialSubmissionButton.setIcon(GUIUtilities.loadIcon(appContext.getProperty("welcome.partial.submission.button.title.selected.large.icon")));
            }
        }
    }

}
