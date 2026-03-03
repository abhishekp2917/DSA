import java.util.*;

public class Twitter {

    // Global timestamp to maintain chronological order.
    //
    // Why static?
    // Because all tweets across all users
    // must share a global increasing time.
    private static int timeStamp = 0;

    // userId → User object
    //
    // Why?
    // So we can quickly retrieve user data.
    private Map<Integer, User> userMap;

    // -------------------- Tweet Node --------------------
    //
    // Each Tweet acts like a node in a linked list.
    //
    // Why linked list?
    // Because:
    // - Each user's tweets are stored in reverse chronological order.
    // - New tweets are inserted at head.
    // - Makes merging very efficient.
    private class Tweet {

        public int id;
        public int time;
        public Tweet next;

        public Tweet(int id) {
            this.id = id;

            // Assign global increasing timestamp.
            this.time = timeStamp++;

            next = null;
        }
    }

    // -------------------- User Class --------------------
    //
    // Encapsulates:
    // - Follow relationships
    // - Tweets
    //
    // This is good OO design:
    // Each user manages their own state.
    public class User {

        public int id;

        // Set of users this user follows.
        //
        // Why Set?
        // To avoid duplicate follow relationships.
        public Set<Integer> followed;

        // Head of linked list of tweets.
        //
        // Newest tweet always at head.
        public Tweet tweet_head;

        public User(int id) {
            this.id = id;

            followed = new HashSet<>();

            // Important:
            // User follows itself by default.
            //
            // Why?
            // So its own tweets appear in news feed.
            follow(id);

            tweet_head = null;
        }

        public void follow(int id) {
            followed.add(id);
        }

        public void unfollow(int id) {
            followed.remove(id);
        }

        // Insert new tweet at head.
        //
        // Why head insertion?
        // So most recent tweet is always first.
        public void post(int id) {
            Tweet t = new Tweet(id);
            t.next = tweet_head;
            tweet_head = t;
        }
    }

    // -------------------- Constructor --------------------
    public Twitter() {
        userMap = new HashMap<>();
    }

    // -------------------- Post Tweet --------------------
    public void postTweet(int userId, int tweetId) {

        // Create user if not present.
        if (!userMap.containsKey(userId)) {
            userMap.put(userId, new User(userId));
        }

        userMap.get(userId).post(tweetId);
    }

    // -------------------- Get News Feed --------------------
    //
    // Core logic:
    // Merge k sorted linked lists (one per followed user)
    // and extract top 10 most recent tweets.
    public List<Integer> getNewsFeed(int userId) {

        List<Integer> res = new LinkedList<>();

        if (!userMap.containsKey(userId))
            return res;

        // Get all users this user follows (including itself).
        Set<Integer> users =
            userMap.get(userId).followed;

        // Max heap ordered by tweet timestamp.
        //
        // Why max heap?
        // Because we want most recent tweet first.
        PriorityQueue<Tweet> pq =
            new PriorityQueue<>(
                (a, b) -> b.time - a.time
            );

        // Insert head tweet of each followed user.
        //
        // Why only head?
        // Because tweets are sorted by time.
        // Head is most recent tweet for that user.
        for (int user : users) {

            Tweet t =
                userMap.get(user).tweet_head;

            if (t != null)
                pq.add(t);
        }

        int count = 0;

        // Extract up to 10 most recent tweets.
        while (!pq.isEmpty() && count < 10) {

            Tweet t = pq.poll();

            res.add(t.id);

            count++;

            // Add next tweet from same user.
            //
            // This is exactly like
            // merging k sorted lists.
            if (t.next != null)
                pq.add(t.next);
        }

        return res;
    }

    // -------------------- Follow --------------------
    public void follow(int followerId, int followeeId) {

        if (!userMap.containsKey(followerId))
            userMap.put(followerId,
                        new User(followerId));

        if (!userMap.containsKey(followeeId))
            userMap.put(followeeId,
                        new User(followeeId));

        userMap.get(followerId)
               .follow(followeeId);
    }

    // -------------------- Unfollow --------------------
    public void unfollow(int followerId,
                         int followeeId) {

        // Cannot unfollow itself.
        if (!userMap.containsKey(followerId)
                || followerId == followeeId)
            return;

        userMap.get(followerId)
               .unfollow(followeeId);
    }
}
