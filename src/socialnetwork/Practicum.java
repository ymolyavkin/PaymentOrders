package socialnetwork;

import com.google.gson.Gson;

public class Practicum {
    public static void main(String[] args) {
        String lastLikeInfoStr = "{ \"user\": \"Алексей\", \"hours\": 12, \"minutes\": 30}";
        Gson gson = new Gson();
       LastLikeInfo lastLikeInfo = gson.fromJson(lastLikeInfoStr, LastLikeInfo.class);

        //LastLikeInfo lastLikeInfo = // код десериализации

        LikesInfo likesInfo = new LikesInfo();
        likesInfo.setRepostsCount(10);
        likesInfo.setHasOwnerLiked(true);
        likesInfo.setLastLikeInfo(lastLikeInfo);
        likesInfo.setLikes(new Like[]{
                new Like("Алексей", "http://example.com/avatars/aleksey.jpg"),
                new Like("Елена", "http://example.com/avatars/elena.jpg"),
                new Like("Света", "http://example.com/avatars/sveta.jpg"),
        });
/**
 * // создайте экземпляр Gson
 *         Gson gson = new Gson();
 *         // сериализуйте объект
 *         String postSerialized = gson.toJson(post);
 *         // String postSerialized = ...;
 *         System.out.println("Serialized post: " + postSerialized);
 *
 *         // десериализуйте объект
 *         // UserPost postDeserialized = ...;
 *         UserPost postDeserialized = gson.fromJson(postSerialized, UserPost.class);
 *         System.out.println("Deserialized post: " + postDeserialized);
 */
        // код сериализации
        String likesInfoSerialized = gson.toJson(likesInfo);
          // String postSerialized = ...;
          System.out.println("Serialized likesInfo: " + likesInfoSerialized);
    }
}
