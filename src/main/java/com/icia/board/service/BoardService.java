package com.icia.board.service;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardFileDTO;
import com.icia.board.dto.PageDTO;
import com.icia.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberService memberService;

    public List<BoardDTO> pagingList(int page) {
        int pageLimit = 3; // 한페이지에 보여줄 글의 갯수 (두번째 숫자)
        int pagingStart = (page-1) * pageLimit; // db에서 첫번째 숫자
        Map<String, Integer> pagingParam = new HashMap<>();
        pagingParam.put("start", pagingStart);
        pagingParam.put("limit", pageLimit);
        List<BoardDTO> boardDTOList = boardRepository.pagingList(pagingParam);
        return boardDTOList;

    }

    public PageDTO pagingParam(int page) {
        int pageLimit = 3; // 한페이지에 보여줄 글 갯수
        int blockLimit = 3; // 하단에 보여줄 페이지 번호 갯수
        // 화면 하단에 글페이지수 나타내기
        //1. 전체 글 갯수 조회
        int boardCount = boardRepository.boardCount();
        // 전체 페이지 갯수 계산
//        int maxPage = boardCount / 3; // pagingList의 pagelimit이랑 같음, 자바는 소수점을 버림
        int maxPage = (int) (Math.ceil((double)boardCount / pageLimit));
        // Math.ceil올림처리를 하는 메서드
        //시작 페이지 값 계산 (1, 4, 7, 10 ···)
        // 현재 사용자의 페이지를 알고 있어야 함
        int startPage = (((int)(Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        //마지막 페이지 값 계산 (3, 6, 9, 12 ···)
        int endPage = startPage + blockLimit - 1;
        // 전체 페이지 갯수가 계산한 endPage 보다 작을때는 emdPage 값을 maxPage 값과 갇게 세팅
        if(endPage > maxPage){
            endPage = maxPage;
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        pageDTO.setMaxPage(maxPage);
        pageDTO.setEndPage(endPage);
        pageDTO.setStartPage(startPage);
        return pageDTO;
    }

    public List<BoardDTO> searchList(String q, String type, int page) {
        int pageLimit = 5; // 한페이지에 보여줄 글 갯수
        int pagingStart = (page-1) * pageLimit;
        Map<String, Object> pagingParams = new HashMap<>(); //String값과 int값을 모두 받아야 하기에 Object로 설정
        pagingParams.put("start", pagingStart);
        pagingParams.put("limit", pageLimit);
        pagingParams.put("q", q);
        pagingParams.put("type", type);
        List<BoardDTO> boardDTOList = boardRepository.searchList(pagingParams);
        return boardDTOList;
    }

    public PageDTO pagingSearchParam(int page, String type, String q) {
        int pageLimit = 5; // 한페이지에 보여줄 글 갯수
        int blockLimit = 3; // 하단에 보여줄 페이지 번호 갯수
        Map<String, Object> pagingParams = new HashMap<>();
        pagingParams.put("q", q);
        pagingParams.put("type", type);
        // 전체 글 갯수 조회
        int boardCount = boardRepository.boardSearchCount(pagingParams);
        // 전체 페이지 갯수 계산
        int maxPage = (int) (Math.ceil((double)boardCount / pageLimit));
        // 시작 페이지 값 계산(1, 4, 7, 10 ~~)
        int startPage = (((int)(Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        // 마지막 페이지 값 계산(3, 6, 9, 12 ~~)
        int endPage = startPage + blockLimit - 1;
        // 전체 페이지 갯수가 계산한 endPage 보다 작을 때는 endPage 값을 maxPage 값과 같게 세팅
        if (endPage > maxPage) {
            endPage = maxPage;
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        pageDTO.setMaxPage(maxPage);
        pageDTO.setEndPage(endPage);
        pageDTO.setStartPage(startPage);
        return pageDTO;
    }

    public void boardSave(BoardDTO boardDTO) throws IOException {
        //파일 있음(1), 없음(0).
        //파일이 비었으면
        //BoardFile은 리스트 객체이니까 get(0)은 0번 인덱스의 파일을 가져와서 없으면 isEmpty
        if(boardDTO.getBoardFile().get(0).isEmpty()){
            System.out.println("파일없음");
            boardDTO.setFileAttached(0);
            boardRepository.boardSave(boardDTO);
        }else{

            System.out.println("파일있음");
            boardDTO.setFileAttached(1);
            //dto는 사용자가 입력한 인풋값을 담음(아이디가값이 담겨있음)
            BoardDTO dto = boardRepository.boardSave(boardDTO);
            //파일이 여러개니까 반복문을 돌려야됌
            //여러개의 파일을 하나씩 처리해줘야됌
            for(MultipartFile boardFile: boardDTO.getBoardFile()) {

                // 원본 파일 이름 가져오기
                String originalFilename = boardFile.getOriginalFilename();
                System.out.println("originalFilename = " + originalFilename);

                // 저장용 이름 만들기

                String storedFileName = System.currentTimeMillis() + "-" + originalFilename;
                System.out.println("storedFileName = " + storedFileName);

                //저장을 위한 BoardFileDTO세팅
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFilename);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(dto.getId());

                //로컬에 파일 저장
                //저장할 경로 설정(저장할 폴더 + 저장할 이름)
                //어디(큰따옴표안에 쓴곳)에 어떤 이름(storedFileName)으로 저장할지 경로를 만들어줌
                // 큰따옴표 안에 젤 뒤에 백슬래시(\\) 두개 추가 필수 꼮꼮꼮꼬꼮ㄲ!!
                String savePath = "D:\\springframework_img\\" + storedFileName;

                //저장처리
                // 리스트에 담겨있는데 for문으로 접근하고있으므로 각각 첨부파일로 접근함
                boardFile.transferTo(new File(savePath));

                //boardFileDTO여기에는 인풋에서 입력받아온 원본파일이름이랑, 저장파일이름, 아이디값이 들어있음
                boardRepository.saveFile(boardFileDTO);
            }


        }

    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }

    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return boardRepository.findFile(id);
    }
}
