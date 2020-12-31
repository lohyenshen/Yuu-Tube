package operation;
import java.util.Scanner;

import app.User;
import app.Video;
import database.Query;
import database.SearchQuery;
import database.SubscriberQuery;

public class Search{
    public static void main( User currentUser) throws Exception{
        Scanner sc = new Scanner(System.in);
        char c;
        do{
            System.out.println();
            System.out.println("Enter                           \'e\' to exit searching:");
            System.out.print("Enter \'u\' to search Users, Enter \'v\' to search Videos: ");

            c = sc.nextLine().charAt(0);
            if (c == 'e'){
                System.out.println("Exit searching");
                return;
            }
        } while ( !(c=='u' || c=='v'));


        // supports subscribing to multiple users at a time
        // as long as currentUser has not subscribed to a channel
        // a channel can only be subscribed by 1 user 1 time
        // a channel cannot be subscribed by the SAME USER multiple times !
        if (c == 'u'){
            String s;
            do{
                System.out.print("Search NAME of Users: ");
                s = sc.nextLine();

                if (s.isBlank() || s.isEmpty())
                    System.out.println("search cannot be EMPTY or BLANK!");
            } while( s.isBlank() || s.isEmpty() );

            do{
                User[] searchedUsers  = SearchQuery.searchUsers( s , currentUser );
                printSearchResult( searchedUsers );

                boolean validID = false;
                System.out.println();
                System.out.println("Exit subscribing ,Enter \'e\'");
                System.out.print("To subscribe to a user, Enter userID: ");
                String op = sc.next();
                if (op.equals("e"))
                    return;

                int id;
                if (isInteger( op )){
                    id = Integer.parseInt( op );
                }
                else{
                    System.out.println("Enter a valid ID !");
                    continue;
                }

                for (User searchedUser : searchedUsers) {
                    if (id == searchedUser.getUserID()) {

                        // currentUser cannot subscribe to himself/herself
                        if (currentUser.getUserID() == searchedUser.getUserID()) {
                            System.out.println("Cannot subscribe to yourself !");
                            break;
                        }
                        // currentUser wants to subscribe to the searchedUser
                        //     (a)                                  (b)
                        // but has (a)    subscribed to             (b) before ?
                        else {
                            validID = true;

                            // if (a) has subscribed to (b) before
                            // (a) will now unsubscribes to b
                            if ( searchedUser.getSubscribed() ) {

                                // delete the record where "currentUser" subscribes to "searchedUser"
                                SubscriberQuery.delete( currentUser.getUserID() , searchedUser.getUserID() );
                                // decrease subscribersCount of the searchedUser
                                Query.decrease("user", "subscribersCount",  1, "userID", searchedUser.getUserID());

                                System.out.println("UNSUBSCRIBED successfully from " + searchedUser.getName());
                                break;
                            }
                            // (a) has not subscribed to (b) before
                            else {
                                // insert a new record where "currentUser" subscribes to "searchedUser"
                                SubscriberQuery.insertNew( currentUser.getUserID() , searchedUser.getUserID() );
                                // increase subscribersCount of the searchedUser
                                Query.increase("user", "subscribersCount",  1, "userID", searchedUser.getUserID());
                                System.out.println("SUBSCRIBED successfully to " + searchedUser.getName());
                                break;
                            }
                        }
                    }
                }
                if (!validID)
                    System.out.println("Enter a valid ID !");
            } while (true);
        }

        // does not support playing multiple videos simultaneously
        // because we have to display statistics for each video played
        else{
            String s;
            do{
                System.out.print("Search TITLE of Videos: ");
                s = sc.nextLine();

                if (s.isBlank() || s.isEmpty())
                    System.out.println("search cannot be EMPTY or BLANK!");
            } while( s.isBlank() || s.isEmpty() );

            Video[] searchedVideos  = SearchQuery.searchVideos( s );
            printSearchResult( searchedVideos );
            do{
                boolean validID = false;
                System.out.println();
                System.out.println("Exit playing ,Enter \'e\'");
                System.out.print("To play a video, Enter videoID: ");

                String op = sc.next();
                if (op.equals("e"))
                    return;

                int id;
                if (isInteger( op )){
                    id = Integer.parseInt( op );
                }
                else{
                    System.out.println("Enter a valid ID !");
                    continue;
                }

                for (Video searchedVideo : searchedVideos) {
                    if (id == searchedVideo.getVideoID()) {
                        validID = true;

                        PlayVideo.withLogin( currentUser, searchedVideo );
                        // after a video is played, return to MainPage
                        // does not support playing multiple videos simultaneously
                        return;
                    }
                }
                if (!validID)
                    System.out.println("Enter a valid ID !");
            } while (true);
        }
    }
    public static void printSearchResult( User[] users){
        // for fun, depends on GUI
        // search result will not print all fields of an User object (privacy issue)
        // it will only display (userID, name, videosCount, subscribersCount)

        System.out.println();
        System.out.println("Search Result (User's name)");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%10s%30s%20s%20s%20s\n", "User ID", "Name", "Videos Count", "Subscribers Count", "Subscription");
        System.out.println("----------------------------------------------------------------------------------------------------");
        for (User user : users) {
            System.out.printf("%10d%30s%20d%20d", user.getUserID(), user.getName(), user.getVideosCount(), user.getSubscribersCount());
            System.out.printf("%20s", ( user.getSubscribed() ? "SUBSCRIBED" : "" ) );
            System.out.println();
        }
    }
    public static void printSearchResult( Video[] videos){
        // for fun, depends on GUI
        // search result will not print all fields of an Video object (privacy issue)
        // it will print all fields except (path, userID, comments)
        // (path) is for our program to locate the video files
        // (userID) is used in database
        // (comments) will be displayed when user play video

        System.out.println();
        System.out.println("Search Result (Video's title)");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%10s%30s%20s%20s%20s\n", "Video ID", "Title", "Likes Count", "Dislikes Count", "Views Count");
        System.out.println("----------------------------------------------------------------------------------------------------");
        for (Video video : videos )
            System.out.printf("%10d%30s%20d%20d%20d\n", video.getVideoID(), video.getTitle(), video.getLikesCount(), video.getDislikesCount(), video.getViewsCount() );
    }
    public static boolean isInteger(String s){
        // verifies whether or not a String entered is a valid Integer

        int L = s.length();
        for (int i=0; i < L ; i++){
            if ( !Character.isDigit( s.charAt(i) ) ){
                return  false;
            }
        }
        return L > 0; // return false if L is 0 (empty string)
                      // else, return true because it is (not an empty string) and (all its elements are digits)
    }
}
