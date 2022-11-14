# Contribute Guide
일반적인 Git Flow를 따릅니다.
<img width="80%" src="https://user-images.githubusercontent.com/30999955/201459363-09021409-c272-4d66-8588-d29a180cc3ce.svg" />
- main branch - 최종 제출용
- develop branch - 개발 진행용
- feature branch - 기능 개발용

## Branch naming
> 브랜치 이름을 지을때는 다음과 같이 진행합니다.
- feat/{username}/{feature-name}
- fix/{username}/{feature-name}
- refac/{username}/{feature-name}
- test/{username}/{feature-name}

## Commit naming
> 아래 commit message naming은 필수는 아닙니다.
- feat: 기능 개발
- fix: 오류 수정
- chore: 환경설정 관련 수정
- refac: 코드 개선
- docs: 문서
- typo: 오탈자 수정
- lint: 코드 스타일 수정


## Branch PR merge 관련
- Conflict는 각자 브랜치에서 해결합니다.
- `develop` 브랜치에 머지 사항이 발생하는 경우 내 개발 브랜치로 "merge" 합니다.
- 개발 완료 브랜치는 삭제하지 않습니다. (이는 수업 발표를 위하여 남겨둡니다.)
- PR merge는 "Create merge commit" 방식만 사용합니다.
- `main`, `develop` 브랜치는 push를 막아뒀습니다. PR만 사용해주세요
- `main`, `develop` 브랜치의 PR merge는 2인의 리뷰를 필수적으로 요구합니다.