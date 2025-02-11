package filemanagercli;

import filemanagercli.models.VirtualFile;
import filemanagercli.models.VirtualDirectory;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 가상의 파일 시스템을 관리하는 클래스
 */
public class VirtualFileSystem {
    private final Map<String, VirtualFile> files;
    private final Map<String, String> filePermissions;
    private final Map<String, String> fileOwners;
    private final Map<String, String> fileGroups;
    private final Map<String, String> fileContents;
    private String currentPath = "/empty_project";

    public VirtualFileSystem() {
        this.files = new HashMap<>();
        this.filePermissions = new HashMap<>();
        this.fileOwners = new HashMap<>();
        this.fileGroups = new HashMap<>();
        this.fileContents = new HashMap<>();
        initializeDummyData();
    }

    private void initializeDummyData() {
        addFile("/empty_project", true, "drwxr-xr-x", "root", "root", "");
        addFile("/empty_project/data", true, "drwxr-xr-x", "root", "root", "");
        addFile("/empty_project/src", true, "drwxr-xr-x", "root", "root", "");
        addFile("/empty_project/logs", true, "drwxr-xr-x", "root", "root", "");
        addFile("/empty_project/docs", true, "drwxr-xr-x", "root", "root", "");
        addFile("/empty_project/tests", true, "drwxr-xr-x", "root", "root", "");
        addFile("/empty_project/scripts", true, "drwxr-xr-x", "root", "root", "");

        addFile("/empty_project/data/raw_data.csv", false, "-rw-r--r--", "root", "root", "ID,Name,Age\n1,John,30\n2,Jane,28");
        addFile("/empty_project/data/processed_data.json", false, "-rw-r--r--", "root", "root", "{ \"users\": [{\"id\":1,\"name\":\"John\"},{\"id\":2,\"name\":\"Jane\"}]}");
        addFile("/empty_project/data/config.yaml", false, "-rw-r--r--", "root", "root", "settings:\n  mode: production\n  debug: false");

        addFile("/empty_project/src/main.py", false, "-rw-r--r--", "root", "root", "print('Hello, World!')");
        addFile("/empty_project/src/helper.py", false, "-rw-r--r--", "root", "root", "def helper():\n    return 'This is a helper function.'");
        addFile("/empty_project/src/analyzer.py", false, "-rw-r--r--", "root", "root", "def analyze():\n    return 'Analyzing data...'");
        addFile("/empty_project/src/processor.py", false, "-rw-r--r--", "root", "root", "def process():\n    return 'Processing data...'");

        addFile("/empty_project/logs/app.log", false, "-rw-r--r--", "root", "root", "INFO: Application started successfully.");
        addFile("/empty_project/logs/error.log", false, "-rw-r--r--", "root", "root", "ERROR: Something went wrong.");

        addFile("/empty_project/docs/README.md", false, "-rw-r--r--", "root", "root", "# File Manager CLI\nThis is a simple CLI file manager.");
        addFile("/empty_project/docs/requirements.txt", false, "-rw-r--r--", "root", "root", "jline3\nlog4j");
        addFile("/empty_project/docs/changelog.md", false, "-rw-r--r--", "root", "root", "## Changelog\n- Initial release.");

        addFile("/empty_project/tests/test_main.py", false, "-rw-r--r--", "root", "root", "def test_main():\n    assert True");
        addFile("/empty_project/tests/test_helper.py", false, "-rw-r--r--", "root", "root", "def test_helper():\n    assert helper() == 'This is a helper function.'");
        addFile("/empty_project/tests/test_analyzer.py", false, "-rw-r--r--", "root", "root", "def test_analyzer():\n    assert analyze() == 'Analyzing data...'");

        addFile("/empty_project/scripts/setup.sh", false, "-rwxr-xr-x", "root", "root", "#!/bin/bash\necho 'Setting up...'");
        addFile("/empty_project/scripts/run.sh", false, "-rwxr-xr-x", "root", "root", "#!/bin/bash\necho 'Running the application...'");
        addFile("/empty_project/scripts/clean.sh", false, "-rwxr-xr-x", "root", "root", "#!/bin/bash\necho 'Cleaning up...'");
    }

    public void addFile(String path, boolean isDirectory, String permissions, String owner, String group, String content) {
        files.put(path, isDirectory ? new VirtualDirectory(path) : new VirtualFile(path, false));
        filePermissions.put(path, permissions);
        fileOwners.put(path, owner);
        fileGroups.put(path, group);

        if (!isDirectory) {
            fileContents.put(path, content);
        }
    }

    public String getFileContent(String path) {
        return fileContents.getOrDefault(path, "");
    }

    public boolean exists(String path) {
        return files.containsKey(path);
    }

    public void deleteFile(String path) {
        files.remove(path);
        filePermissions.remove(path);
        fileOwners.remove(path);
        fileGroups.remove(path);
        fileContents.remove(path);
    }

    public List<String> listFiles(String directory) {
        if (!files.containsKey(directory) || !files.get(directory).isDirectory()) {
            return Collections.emptyList();
        }

        return files.keySet().stream()
            .filter(path -> path.startsWith(directory) && !path.equals(directory))
            .map(path -> path.replace(directory + "/", ""))
            .sorted()
            .collect(Collectors.toList());
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String path) {
        if (files.containsKey(path) && files.get(path).isDirectory()) {
            this.currentPath = path;
        } else {
            System.out.println("Error: Invalid directory.");
        }
    }

    public Map<String, VirtualFile> getFiles() {
        return files;
    }

    public void moveToParentDirectory() {
        if (currentPath.equals("/empty_project")) {
            System.out.println("Error: Already at root directory.");
            return;
        }
        int lastSlash = currentPath.lastIndexOf('/');
        currentPath = currentPath.substring(0, lastSlash);
    }

    public String resolvePath(String path) {
        if (path.startsWith("/")) {
            return path;
        }
        return currentPath + "/" + path;
    }

}
