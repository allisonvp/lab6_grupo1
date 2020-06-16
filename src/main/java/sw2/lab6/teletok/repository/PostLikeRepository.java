package sw2.lab6.teletok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw2.lab6.teletok.entity.Post;
import sw2.lab6.teletok.entity.PostComment;
import sw2.lab6.teletok.entity.PostLike;
import sw2.lab6.teletok.entity.User;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {

    @Query(value="SELECT count(*) as cantlikes, pl.post_id as postid, p.description as descrip, p.creation_date as dia, p.media_url as foto, u.username as usuario\n" +
            "            FROM post_like pl \n" +
            "            INNER JOIN post p ON p.id=pl.post_id\n" +
            "            INNER JOIN user u ON u.id=p.user_id\n" +
            "            group by pl.post_id",nativeQuery = true)
    int obtenerCantLikesPorPost();

    List<PostLike> findPostLikeByPost(Post post);
    List<PostLike> findPostLikeByPostAndUser(Post post, User user);

}
