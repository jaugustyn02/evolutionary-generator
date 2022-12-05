package agh.ics.opp;

import java.util.List;

public interface IGenomeMutator {
    void mutate(List<Integer> genome);
    void mutate(Animal animal);
}
