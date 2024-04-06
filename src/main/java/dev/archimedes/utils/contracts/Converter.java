package dev.archimedes.utils.contracts;

public interface Converter <P, S>{

    S convert(P p, S s);

    P revert(S s, P p);
}
