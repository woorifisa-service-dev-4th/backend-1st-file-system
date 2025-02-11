# backend-1st-file-system

```bash
./filemanagercli
├── FileManagerCLI.java      # 메인 CLI 실행
├── CommandHandler.java      # 명령어 처리
├── VirtualFileSystem.java   # 가상 파일 시스템 (파일 및 디렉토리 관리)
├── models
│   ├── VirtualFile.java     # 파일 모델
│   ├── VirtualDirectory.java # 디렉토리 모델
│   ├── SymbolicLink.java    # 심볼릭 링크 모델
├── exceptions
│   ├── CommandException.java # 명령어 관련 예외
│   ├── FileSystemException.java # 파일 시스템 관련 예외
├── operations
│   ├── FileOperations.java  # 파일 관련 기능
│   ├── DirectoryOperations.java # 디렉토리 관련 기능
│   ├── PermissionOperations.java # 권한 관련 기능
│   ├── FileSearchOperations.java # 파일 검색 기능
│   ├── FileContentOperations.java # 파일 읽기/쓰기 기능
│   ├── FileSystemInfoOperations.java # 파일 시스템 정보 관련 기능
├── utils
│   ├── CommandUtils.java    # 명령어 유틸리티
│   ├── FileSystemUtils.java # 파일 시스템 유틸리티
│   ├── TerminalUtils.java   # 터미널 관련 유틸리티
└── HelpHandler.java         # 명령어 도움말

```
