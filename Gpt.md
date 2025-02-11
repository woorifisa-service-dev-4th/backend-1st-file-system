1. 자바로 이 모든 걸 구현해야해.
   empty_project 가 루트경로인걸로 생각하고 pwd 하면 /empty_project 가 나오는 걸로 생각해.
   아래는 tree 구조야.

```tree
. empty_project
├── data
│ ├── raw_data.csv
│ ├── processed_data.json
│ └── config.yaml
├── src
│ ├── main.py
│ ├── helper.py
│ ├── analyzer.py
│ └── processor.py
├── logs
│ ├── app.log
│ └── error.log
├── docs
│ ├── README.md
│ ├── requirements.txt
│ └── changelog.md
├── tests
│ ├── test_main.py
│ ├── test_helper.py
│ └── test_analyzer.py
└── scripts
├── setup.sh
├── run.sh
└── clean.sh
```

이걸 더미데이터로 만들고 쓰는게 조건이야

2. 각 기능은 package filemanagercli.commands 에 구현해야해.
   또한 커맨드는 아니지만 공통으로 쓸만한 것들은 filemanagercli.utils에 구현해야해.

3. 저 파일 구조는 main에 기본적으로는 가상 시스템에서 동작하는걸로 생각하고 package filemanagercli에 VirtualFileSystem.java에 이를 구현하되, 실제 경로에서 동작하도록 하는 것도 만들어야해. 실제 경로에서 동작하도록 하는 부분은 주석처리된 상태로 남겨줘.

4. 이거 pom.xml이 있는 빈 maven project이고 현재

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example.filemanager</groupId>
  <artifactId>filemanagercli</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
  </properties>
  <dependencies>
    <dependency>
        <groupId>org.jline</groupId>
        <artifactId>jline</artifactId>
        <version>3.21.0</version>
    </dependency>
</dependencies>
</project>
```

이렇게 썼으니까

입력창에 화살표 누르면 이전 출력값으로 넘어가는게 아니라 위치는 입력 칸에 고정되고 tab 키 동작이나 화살표 동작도 실제 커맨드창처럼 구현해야해.

---
