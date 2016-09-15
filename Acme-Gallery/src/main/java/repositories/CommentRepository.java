package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from Comment c where c.painting.id=?1 and c.parent=null")
	Collection<Comment> findInitialComments(int paintingId);
	
	// Free Services. FR-26
	// Find the comment with the highest number of responses.
	@Query("select c from Comment c where c.children.size = (select max(c.children.size) from Comment c)")
	Collection<Comment> findWithMoreChildrenComments();

	@Query("select c from Comment c where c.parent.id=?1")
	Collection<Comment> findChildren(int commentId);

}
