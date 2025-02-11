## JAVA를 활용한 파일 탐색기
### ✨ 요구사항
#### 📌 ls (파일 목록 출력)
- [ ] 현재 디렉토리에 있는 파일 및 폴더 목록을 출력해야 한다.
- [ ] 파일은 `[F]`, 폴더는 `[D]`로 표시하여 구분한다.
- [ ] 출력은 이름순(알파벳 오름차순)으로 정렬한다.
- [ ] 디렉토리가 비어 있으면 "디렉토리가 비어 있습니다." 메시지를 출력한다.
- [ ] 존재하지 않는 디렉토리를 입력하면 오류 메시지를 출력한다.

실행 예시
```plaintext
/Users/user $ ls
[D] Documents
[D] Downloads
[F] example.txt
[F] notes.pdf
```

#### 📌 cd (디렉토리 이동)
- [ ] 사용자가 입력한 디렉토리로 이동한다.
- [ ] 존재하지 않는 디렉토리일 경우 오류 메시지를 출력한다.
- [ ] `cd ..`을 입력하면 상위 디렉토리로 이동한다.

실행 예시
```plaintext
/Users/user $ cd workspace
/Users/user/workspace $ cd ..
/Users/user $
```

#### 📌 mv (파일 또는 폴더 이동)
- [ ] 파일 또는 폴더를 지정한 위치로 이동해야 한다.
- [ ] `mv <원본파일/폴더> <목적지>` 형식으로 입력 받는다.
- [ ] 원본 파일 또는 폴더가 존재하지 않는 경우 오류 메시지를 출력한다.
- [ ] 목적지가 디렉토리인 경우, 원본 파일/폴더를 해당 디렉토리 안으로 이동해야 한다.

실행 예시
```plaintext
/Users/user $ mv test.txt workspace
파일이 workspace 폴더로 이동되었습니다.
/Users/user $ non-existing.txt workspace
오류: 파일 또는 폴더를 찾을 수 없습니다.
```

### ✅ 실행 방법
터미널에서 `FileApp.jar`가 들어있는 경로로 이동한 뒤, `java -jar FileApp.jar` 입력
