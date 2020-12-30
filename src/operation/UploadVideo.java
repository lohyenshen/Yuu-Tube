package operation;

import app.User;
import app.Video;
import database.VideoQuery;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class UploadVideo {
    public static void main(User currentUser) throws Exception {
        // this method will save video(s) uploaded by a user

        System.out.println("-----Choose video(s) to upload ---------");
        // input file
        File[] fs = chooseFiles();

        // no video file(s) selected
        if (fs == null) {
            System.out.println("-----No video(s) uploaded , returning to main page. ---------");
            return;
        }
        // 1 or more video file(s) selected
        for (File f : fs) {
            // videoID, userID, title, likesCount, dislikesCount, viewsCount, comments, path
            // all new videos has 0 likesCount, 0 dislikesCount, 0 viewsCount, "" comment
            // videoID's ACTUAL VALUE will be handled by MySQL auto increment feature
            int videoID = 0;
            int userID = currentUser.getUserID();
            String title = FilenameUtils.removeExtension(f.getName());
            int likesCount = 0;
            int dislikesCount = 0;
            int viewsCount = 0;
            String[] comments = null;
            String path = currentUser.getName() + "\\" + f.getName();

            Video video = new Video(videoID, userID, title, likesCount, dislikesCount, viewsCount, comments, path);
            // insert video details into database
            VideoQuery.insertNew(video);
            // copy the video file
            String from = f.getAbsolutePath();
            String to = System.getProperty("user.dir") + "\\videos\\" + path;
            toDirectory(from, to);

            System.out.println();
            System.out.println("Upload video done! ");
            System.out.println("Title             = " + title);
            System.out.println("Uploaded by       = " + currentUser.getName());
        }
    }

    public static File[] chooseFiles() throws Exception {
        // opens a window that allows user to choose which video(s) to be uploaded

        File[] fs = null;
        boolean isUniqueTitle = true;

        do {
            JFileChooser fc = new JFileChooser();
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileFilter(new FileNameExtensionFilter("FLV files", "flv"));
            fc.setFileFilter(new FileNameExtensionFilter("WMV files", "wmv"));
            fc.setFileFilter(new FileNameExtensionFilter("AVI files", "avi"));
            fc.setFileFilter(new FileNameExtensionFilter("MOV files", "mov"));
            fc.setFileFilter(new FileNameExtensionFilter("MKV files", "mkv"));
            fc.setFileFilter(new FileNameExtensionFilter("MP4 files", "mp4"));
//                File wd = new File(System.getProperty("user.dir"));
            /////////// alter the pathname to change JFileChooser open location ///////////////////
//            File cd = new File("C:\\Users\\lohye\\Desktop\\videossss\\");
            File cd = new File(System.getProperty("user.home"));
            //////////////////////////////////////////////////////////////////////////////////////
            fc.setCurrentDirectory(cd);
            fc.setMultiSelectionEnabled(true);
            fc.setDialogTitle("Choose video file(s) to upload");
            fc.setApproveButtonText("Upload video(s)");


            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.CANCEL_OPTION) {
                fs = null;
                break;
            }
            if (result != JFileChooser.APPROVE_OPTION)
                continue;

            fs = fc.getSelectedFiles();
            Video[] videos = VideoQuery.getVideos();
            isUniqueTitle = true;
            for (File f : fs) {
                String title = FilenameUtils.removeExtension(f.getName());
                for (Video video : videos) {
                    if ( title.equals( video.getTitle() ) ) {
                        System.out.println("Title = " + title);
                        System.out.println("Video of the SAME TITLE has already been uploaded !");
                        isUniqueTitle = false;

                        break;
                    }
                }
            }
        } while (!isUniqueTitle);

        return fs;
    }
    public static String getFileExtension(File f) {
        // return the extension of a file
        // hello.mp4 returns .mp4

        String fname = f.getName();
        int i = fname.lastIndexOf(".");

        //            i < 0              or            i = 0
        if (i > 0) // rejects filename without "." and rejects filename starts with "."
            return fname.substring(i);
        return "";
    }
    public static void toDirectory (String from, String to) throws Exception {
        // make a copy of video uploaded to  "current_working_directory\videos"
        //                                    this is where all the videos are stored

        final int BUFFERSIZE = 5 * 1024;
        FileInputStream fin = new FileInputStream(new File(from));
        FileOutputStream fout = new FileOutputStream(new File(to));

        byte[] buffer = new byte[BUFFERSIZE];

        while (fin.available() != 0) {
            fin.read(buffer);
            fout.write(buffer);
        }
    }
}
