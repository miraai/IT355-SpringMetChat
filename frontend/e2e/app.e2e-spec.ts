import { KuracPage } from './app.po';

describe('kurac App', function() {
  let page: KuracPage;

  beforeEach(() => {
    page = new KuracPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
