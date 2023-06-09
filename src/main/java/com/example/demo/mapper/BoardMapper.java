package com.example.demo.mapper;

import java.io.*;
import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface BoardMapper {

	@Select("SELECT id, title, writer, inserted FROM Board ORDER BY id DESC")
	List<Board> selectAll();

	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'"/>
			SELECT b.*, count(f.id) fileCount,
			(SELECT COUNT(*) FROM BoardLike WHERE boardId = b.id) likeCount,
			(SELECT COUNT(*) FROM Comment WHERE boardId = b.id) commentCount
			FROM Board b LEFT JOIN FileName f ON b.id = f.boardId
			
			<where>
			<if test="type eq 'all' or type eq 'title'">
			title LIKE #{pattern}
			</if>
			<if test="type eq 'all' or type eq 'writer'">
			OR writer LIKE #{pattern}
			</if>
			<if test="type eq 'all' or type eq 'body'">
			OR body LIKE #{pattern}
			</if>
			</where>
			
			GROUP BY b.id
			ORDER BY b.id DESC
			LIMIT #{startIndex}, #{rowPerPage}
			</script>
			""")
	@ResultMap("boardViewMap")
	List<BoardView> selectAllPaging(Integer startIndex, Integer rowPerPage, String search, String type);

	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'"/>
			SELECT COUNT(id) count
			FROM Board
			
			<where>
			<if test="type eq 'all' or type eq 'title'">
			title LIKE #{pattern}
			</if>
			<if test="type eq 'all' or type eq 'writer'">
			OR writer LIKE #{pattern}
			</if>
			<if test="type eq 'all' or type eq 'body'">
			OR body LIKE #{pattern}
			</if>
			</where>
			
			</script>
			""")
	Integer countAll(String search, String type);

	@Select("""
			SELECT b.id, b.title, b.body, b.writer, b.inserted, f.fileName,
			(SELECT COUNT(*) FROM BoardLike WHERE boardId = b.id) likeCount
			FROM Board b 
			LEFT JOIN FileName f ON b.id = f.boardId
			WHERE b.id = #{id}
			""")
	@ResultMap("boardResultMap")
	Board selectById(Integer id);

	@Update("UPDATE Board SET title = #{title}, body = #{body} "
			+ "WHERE id = #{id}")
	int update(Board board);

	@Delete("DELETE FROM Board WHERE id = #{id}")
	int deleteById(Integer id);

	@Insert("INSERT INTO Board (title, body, writer) "
			+ "VALUES (#{title}, #{body}, #{writer})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(Board board);

	// 파일관련
	@Insert("INSERT INTO FileName (boardId, fileName) "
			+ "VALUES (#{id}, #{fileName})")
	Integer insertFileName(Integer id, String fileName);

	@Select("SELECT fileName FROM FileName WHERE boardId = #{boardId}")
	List<String> selectFileNamesByBoardId(Integer boardId);

	@Delete("DELETE FROM FileName WHERE boardId = #{boardId}")
	void deleteFileNameByBoardId(Integer boardId);

	@Delete("DELETE FROM FileName WHERE boardId = #{boardId} AND fileName = #{fileName}")
	void deleteFileNameByBoardIdAndFileName(Integer boardId, String fileName);

	@Select("""
			SELECT id
			FROM Board
			WHERE writer = #{writer}
			""")
	List<Integer> selectIdByWriter(String writer);

	//이전글 다음글 혼자 만든것
	@Select("""
			SELECT * FROM Board
			WHERE inserted < (SELECT inserted FROM Board where id = #{id})
			ORDER BY inserted DESC
			LIMIT 1;
			""")
	Integer selectPrevId(Integer id);

	@Select("""
			SELECT * FROM Board
			WHERE inserted > (SELECT inserted FROM Board where id = #{id})
			ORDER BY inserted ASC
			LIMIT 1;
			""")
	Integer selectNextId(Integer id);

	@Select("""
			SELECT * FROM Board ORDER BY inserted ASC LIMIT 1
			""")
	Integer selectFirstBoardId();
	
	@Select("""
			SELECT * FROM Board ORDER BY inserted DESC LIMIT 1
			""")
	Integer selectLastBoardId();
}

/*
 * @Select(""" <script> <bind name="pattern" value="'%' + search + '%'"/> SELECT
 * * FROM BoardView <where> <if test="type eq 'all' or type eq 'title'"> title
 * LIKE #{pattern} </if> <if test="type eq 'all' or type eq 'writer'"> OR writer
 * LIKE #{pattern} </if> <if test="type eq 'all' or type eq 'body'"> OR body
 * LIKE #{pattern} </if> </where> ORDER BY id DESC LIMIT #{startIndex},
 * #{rowPerPage} </script> """)
 * 
 * @ResultMap("boardViewMap") List<BoardView> selectAllPaging(Integer
 * startIndex, Integer rowPerPage, String search, String type);
 */
