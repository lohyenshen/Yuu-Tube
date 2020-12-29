package app;

import database.LikeDislikeQuery;
import database.VideoQuery;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.apache.commons.io.*;

public class Video {
    private int videoID;                // unique, auto_increased, PRIMARY KEY in database
    private int userID;                 //                       , FOREIGN KEY in database that reference to user table's PRIMARY KEY

    private String title;
    private int likesCount;
    private int dislikesCount;
    private int viewsCount;
    private String[] comments;
    private String path;

    private static Video[] videos;

    // constructor
    public Video(int videoID, int userID, String title, int likesCount, int dislikesCount, int viewsCount, String[] comments, String path) {
        this.videoID = videoID;
        this.userID = userID;
        this.title = title;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.viewsCount = viewsCount;
        this.comments = comments;
        this.path = path;
    }

    // getters
    public int getVideoID() {
        return videoID;
    }

    public int getUserID() {
        return userID;
    }

    public String getTitle() {
        return title;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getDislikesCount() {
        return dislikesCount;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public String[] getComments() {
        return comments;
    }

    public String getPath() {
        return path;
    }

    public static void printVideoHeader(){
        // for fun, depends on GUI

        String header  = String.format("%20s|%20s|%60s|%20s|%20s|%20s|\n", "videoID", "userID", "title", "likesCount", "dislikesCount", "viewsCount");
        String m = "";
        for (int i = 0; i < 166; i++)
            m += "-";
        m += "\n";

        System.out.print(m + header + m);
    }

    public String toString(){
        // for fun, depends on GUI
        // videoID, userID, title, likesCount, dislikesCount, viewsCount,

        String details = String.format("%20d|%20d|%60s|%20d|%20d|%20d", videoID, userID, title, likesCount, dislikesCount, viewsCount);
        return details;
    }


    // methods
    public static void uploadVideo(User currentUser) throws Exception {
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
            String path = f.getName();

            Video video = new Video(videoID, userID, title, likesCount, dislikesCount, viewsCount, comments, path);
            // insert video details into database
            VideoQuery.insertNew(video);
            // copy the video file
            String from = f.getAbsolutePath();
            String to = System.getProperty("user.dir") + "\\videos\\" + path;
            copyVideo(from, to);

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
            fc.setFileFilter(new FileNameExtensionFilter("MP4 files", "mp4"));
//                File wd = new File(System.getProperty("user.dir"));
            /////////// alter the pathname to change JFileChooser open location ///////////////////
            File cd = new File("C:\\Users\\lohye\\Desktop\\videossss\\");
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
            videos = VideoQuery.getVideos();
            isUniqueTitle = true;
            for (File f : fs) {
                String title = FilenameUtils.removeExtension(f.getName());
                for (Video video : videos) {
                    if (title.equals(video.title)) {
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
    public static void copyVideo(String from, String to) throws Exception {
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
    public void printStatistics( User currentUser ) throws Exception{
        // this code is just for fun
        // actual implementation depends on GUI

        System.out.println();
        System.out.printf("%50s|%20s|%20s|%20s|\n", "Title", "Views Count", "Likes Count", "Dislikes Count" );
        for (int i = 0; i < 114; i++)
            System.out.print("-");
        System.out.println();
        System.out.printf("%50s|%20d|%20d|%20d|\n", title, viewsCount, likesCount, dislikesCount);


        System.out.println();
        if ( LikeDislikeQuery.likedDisliked( currentUser, this)){
            boolean status = LikeDislikeQuery.getStatus( currentUser, this);
            if (status){
                System.out.println("You LIKED this video before");
            }
            else{
                System.out.println("You DISLIKED this video before");
            }
        }

        System.out.println();
        System.out.println("Comments");
        for (int i = 0; i < 114; i++)
            System.out.print("-");
        System.out.println();
        // comments of new video is always initialized with ""
        // therefore we need to skip the first comment, comments[0]
        for (int i = 1; i < comments.length; i++) {
            System.out.println( (i) + ") " + comments[i] );
        }
        System.out.println();
    }
}