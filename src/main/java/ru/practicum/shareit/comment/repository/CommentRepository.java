package ru.practicum.shareit.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.comment.dto.CommentResponseDto;
import ru.practicum.shareit.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("""
            SELECT new ru.practicum.shareit.comment.dto.CommentResponseDto(
                                  c.id,
                                  c.text,
                                  c.author.name,
                                  c.createdAt
                              )
            FROM Comment c
            WHERE c.item.id = ?1
            """)
    List<CommentResponseDto> getCommentsByItemId(Long itemId);
}
