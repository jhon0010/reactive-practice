import com.practice.reactor.spring.domain.Comment
import com.practice.reactor.spring.domain.User
import spock.lang.Specification
import spock.lang.Unroll

class HelloSpock extends Specification {

    def "Basic" (){
        expect: "Two values are equals"
        2 == 2
    }

    def "one plus one should equal two"() {
        expect:
        1 + 1 == 2
    }

    /**
     * Data Driven Testing
     *
     * Use unroll for run separate test for each
     * value in the where clause.
     */
    @Unroll
    def "Expected sum result " () {
        expect:
        num1 + num2 == num3

        where: "Separate the values by | and then the expected result by ||"
        num1 | num2 || num3
        1    |1     || 2
        2    |1     || 3
        1.5  |1.5   || 3
        0.5  |0.5   || 1
    }

    /**
     * Data Driven Testing
     *
     * Using the # symbol in the name of the function you can inject
     * the value of all test cases and see in the console.
     *
     *  Only works with @Unroll
     */
    @Unroll
    def "Expected equality numbers #num1 equals #num2 is #areEqual" () {
        expect:
        (num1) == (num2) == areEqual

        where:
        num1 | num2 || areEqual
        1    |1     || true
        2    |1     || false
        1.5  |1.5   || true
        0.5  |0.5   || true
    }

    @Unroll
    def "numbers to the power of two"(int a, int b, int c) {
        expect:
            Math.pow(a, b) == c
        where:
            a | b | c
            1 | 2 | 1
            2 | 2 | 4
            3 | 2 | 9
    }

    def "Expected number of executions of a method" () {

        given: "A fake Comment class"
            def comment = Mock(Comment)
        when: "The addComment method is called"

            comment.getComments() >> "Return this mocked value"
            comment.getComments()
            comment.addComment("Jhon")
        then:
            1 * comment.addComment("Jhon")
            0 * comment.setComments()
            1 * comment.getComments()
    }

    def "Other mock object" () {

        given: "A fake User class"
            User user = Mock()
            user.getLastName() >> "Jhon"

        when: "The addComment method is called"
            def userName = user.getLastName()
        then:
            userName == "Jhon"
    }

    /**
     * Groovy gives us simpler ways of creating lists. We can just able to declare our
     * elements with square brackets, and internally a list will be instantiated.
     */
    def "Should be able to remove from list"() {
        given:
            def list = [1, 2, 3, 4]
        when:
            list.remove(0)
        then:
            list == [2, 3, 4]
    }

    def "Should get an index out of bounds when removing a non-existent item"() {
        given:
            def list = [1, 2, 3, 4]
        when:
            list.remove(20)
        then:
            thrown(IndexOutOfBoundsException)
            list.size() == 4
    }

}
