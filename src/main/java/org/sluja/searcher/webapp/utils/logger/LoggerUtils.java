package org.sluja.searcher.webapp.utils.logger;

public class LoggerUtils {

    public static String getCurrentMethodName() {
        return StackWalker.getInstance().walk(frames -> frames.skip(1).findFirst().get().getMethodName());
    }

    public static String getCurrentClassName() {
        return StackWalker.getInstance().walk(frames -> {
            StackWalker.StackFrame frame = frames.skip(1).findFirst().get();
            String fullClassName = frame.getClassName();
            return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
        });
    }

}
