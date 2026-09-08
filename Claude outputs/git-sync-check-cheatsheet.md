# Git Cheat Sheet: Checking Branch Sync Status

A quick reference for checking whether your local `main` is up to date with remote, and whether your feature branch is up to date with `main`.

## 1. Update your knowledge of the remote

Before checking anything, fetch the latest refs from the remote (this does *not* change any of your local branches):

```
git fetch origin
```

## 2. Check if local `main` is up to date with remote `main`

```
git checkout main
git status
```

After a fetch, `git status` will tell you directly: "Your branch is up to date with 'origin/main'", or "Your branch is behind 'origin/main' by N commits".

For a script-friendly check, compare commit hashes:

```
git rev-parse main
git rev-parse origin/main
```

If these two hashes match, your local `main` is fully up to date. You can also see the difference directly:

```
git log main..origin/main --oneline
```

Any commits listed here exist on remote but not locally — meaning you're behind and should pull.

To bring local `main` up to date:

```
git pull origin main
```

(or `git merge origin/main` if you already fetched)

## 3. Check if your branch is up to date with local `main`

First make sure local `main` itself is current (steps above), then from your feature branch:

```
git checkout your-branch-name
git log your-branch-name..main --oneline
```

Any commits listed here are on `main` but not yet in your branch — meaning your branch is behind and should be rebased/merged.

The reverse check — commits on your branch not yet on `main` — is useful too:

```
git log main..your-branch-name --oneline
```

For a combined view of how the two branches have diverged:

```
git log --oneline --left-right --graph main...your-branch-name
```

`<` lines are commits only on `main`; `>` lines are commits only on `your-branch-name`.

A quick count-only version:

```
git rev-list --left-right --count main...your-branch-name
```

Output is two numbers: `<commits ahead in main>	<commits ahead in your-branch>`.

## 4. Bring your branch up to date with `main`

Pick one approach:

**Merge** (safe, preserves history, creates a merge commit):

```
git checkout your-branch-name
git merge main
```

**Rebase** (cleaner linear history, rewrites your branch's commits):

```
git checkout your-branch-name
git rebase main
```

## 5. One-shot status summary

`git status` after a fetch summarizes both "ahead/behind" states for the branch you're on, relative to its own upstream. To see this for all local branches at once:

```
git fetch origin
git branch -vv
```

This shows each local branch alongside its tracking branch and how many commits ahead/behind it is.

## Quick reference table

| Question | Command |
|---|---|
| Update remote refs | `git fetch origin` |
| Is local main current? | `git status` (on main, after fetch) |
| Compare main hashes | `git rev-parse main` vs `git rev-parse origin/main` |
| What's new on remote main? | `git log main..origin/main --oneline` |
| Update local main | `git pull origin main` |
| Is my branch behind main? | `git log your-branch..main --oneline` |
| Is my branch ahead of main? | `git log main..your-branch --oneline` |
| Divergence graph | `git log --oneline --left-right --graph main...your-branch` |
| Divergence counts | `git rev-list --left-right --count main...your-branch` |
| All branches at a glance | `git branch -vv` (after fetch) |
| Sync branch with main (merge) | `git merge main` |
| Sync branch with main (rebase) | `git rebase main` |
