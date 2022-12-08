package xyz.lazyrabbit.jdk17;

public class SealedTest {
    /**
     * 密封类
     */
    abstract sealed class Subject permits SubjectA, SubjectB {
    }

    /**
     * 子类非密封类
     */
    non-sealed class SubjectA extends Subject {
    }

    /**
     * 子类也是密封类
     */
    sealed class SubjectB extends Subject permits SubjectC {
    }

    /**
     * 子类为final类，不可被继承
     */
    final class SubjectC extends SubjectB {
    }
}
