package com.example.demo.comment.service;

import com.example.demo.comment.model.Comment;
import com.example.demo.comment.model.dto.ContentCommentRes;
import com.example.demo.comment.model.dto.request.CommentReq;
import com.example.demo.comment.model.dto.request.UpdateCommentReq;
import com.example.demo.comment.model.dto.response.CommentRes;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.content.model.Content;
import com.example.demo.content.repository.ContentRepository;
import com.example.demo.member.model.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final ContentRepository contentRepository;
    private final MemberRepository memberRepository;

    public void createComment(CommentReq commentReq, Member member) {
        Optional<Content> contentResult = contentRepository.findById(commentReq.getContentId());
        Optional<Member> memberResult = memberRepository.findByEmail(member.getEmail());

        Content content = contentResult.get();

        repository.save(Comment.builder()
                .comment(commentReq.getComment())
                .rate(commentReq.getRate())
                .member(memberResult.get())
                .content(content)
                .build());
    }

    public List<CommentRes> list(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Comment> result = repository.findList(pageable);

        List<CommentRes> commentResList = new ArrayList<>();
        for (Comment comment : result.getContent()) {
            CommentRes commentRes = CommentRes.builder()
                    .id(comment.getId())
                    .comment(comment.getComment())
                    .rate(comment.getRate())
                    .member(comment.getMember())
                    .content(ContentCommentRes.builder()
                            .contentId(comment.getContent().getId())
                            .contentName(comment.getContent().getName())
                            .contentImage(comment.getContent().getContentImages().getFilename())
                            .contentClassify(comment.getContent().getClassify())
                            .build())
                    .build();
            commentResList.add(commentRes);
        }
            return commentResList;
    }

    public Comment readComment(Long id) {
        Optional<Comment> result = repository.findById(id);
        Comment comment = result.get();
        return comment;
    }

    public void updateComment(UpdateCommentReq updateCommentReq) {
        Optional<Comment> result = repository.findById(updateCommentReq.getId());
        if (result.isPresent()) {
            Comment comment = result.get();
            if (updateCommentReq.getComment() != null)
                comment.setComment(updateCommentReq.getComment());
            if (updateCommentReq.getRate() != null)
                comment.setRate(updateCommentReq.getRate());
            repository.save(comment);
        }
    }

    public void deleteComment(Long id) {
        Optional<Comment> result = repository.findById(id);
        if (result.isPresent()) ;
        Comment comment = result.get();
        comment.setMember(null);
        comment.setMember(null);
        repository.delete(comment);
    }
}
