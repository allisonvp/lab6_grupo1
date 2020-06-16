package sw2.lab6.teletok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw2.lab6.teletok.entity.Post;
import sw2.lab6.teletok.entity.PostComment;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

    @Query(value="SELECT count(pc.message) as cantlikes, pc.post_id as postid, p.description as descrip, p.creation_date as dia, p.media_url as foto, u.username as usuario\n" +
            "FROM post_comment pc\n" +
            "INNER JOIN post p ON p.id=pc.post_id\n" +
            "INNER JOIN user u ON u.id=p.user_id\n" +
            "group by pc.post_id",nativeQuery = true)
    int obtenerCantComentPorPost();



    List<PostComment> findPostCommentByPost(Post post);
}
