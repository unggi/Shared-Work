package rules.tool;


import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STGroupFile;
import rules.BusinessRulesLexer;
import rules.BusinessRulesParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Hello world!
 */
public class Tool {

    private static final String TEST_FILE = "src/test/resources/parsing//proposed.nrl";

    public static void main(String[] args) {

        System.out.println("Business Rules Processor");
        try {

            if (args.length < 0)
                usageError("");

            File file = new File(TEST_FILE);
            if (!file.exists()) {
                usageError("File not found: " + TEST_FILE);
            }

            ANTLRInputStream input = new ANTLRFileStream(file.getAbsolutePath());

            BusinessRulesLexer lexer = new BusinessRulesLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            BusinessRulesParser parser = new BusinessRulesParser(tokens);
            parser.setBuildParseTree(true);
            parser.addParseListener(new RuleListener());
            BusinessRulesParser.FileBodyContext t = parser.fileBody();

            message("Parsed Successfully : " + TEST_FILE);

            ST hello = new ST("Hello, <name>");
            hello.add("name", "World");
            message(hello.render());

            STGroup g = new STGroupFile("PrettyPrint.stg");
            ST st = g.getInstanceOf("a");
            message("A template produces : " + st.render());

            st = g.getInstanceOf("b");
            message("Next template produces : " + st.render());


        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + ex.toString());
        } catch (IOException iox) {
            System.err.println("IOException: " + iox.toString());
        }

    }


    private static void usageError(String msg) {
        System.err.println(msg);
        System.exit(1);
    }

    private static void message(String msg) {
        System.err.println(msg);
    }
}

// ddd
